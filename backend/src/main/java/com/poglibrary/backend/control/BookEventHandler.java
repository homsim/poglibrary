package com.poglibrary.backend.control;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Service;

import com.poglibrary.backend.control.FetchBookInfo.OpenLibraryClient;
import com.poglibrary.backend.model.Author;
import com.poglibrary.backend.model.Book;
import com.poglibrary.backend.model.Cover;
import com.poglibrary.backend.repository.AuthorRepository;
import com.poglibrary.backend.repository.BookRepository;
import com.poglibrary.backend.repository.CoverRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
@RepositoryEventHandler
public class BookEventHandler {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CoverRepository coverRepository;

    @HandleBeforeCreate
    public void beforeCreate(Book book) throws IOException, URISyntaxException {
        String isbnFormatted = book.getIsbn().replace("-", "");
        book.setIsbn(isbnFormatted);

        /*
         * if only the isbn is given, get the other fields via Open Library API
         * ToDo:
         *  - fallback scenario for another API if Open Library does not find the book
         *  - if book.requestedCover -> get cover from API...
         *    -> add the field and refactor openLibraryClient
         */
        if (book.getAuthors() == null || book.getTitle() == null) {
            //OpenLibraryClient client = new OpenLibraryClient(isbnFormatted);
            OpenLibraryClient client = new OpenLibraryClient(isbnFormatted);

            if (book.getTitle() == null) {
                book.setTitle(client.getEdition().getTitle());
            }

            if (book.getAuthors() == null) {
                Set<Author> authors = new HashSet<Author>();
                for (String clientAuthor : client.getEdition().getAuthorNames()) {
                    Author newAuthor = new Author(clientAuthor);

                    Set<Author> existingAuthors = authorRepository
                            .findByFirstnameAndLastname(newAuthor.getFirstname(),
                                    newAuthor.getLastname());

                    if (existingAuthors.isEmpty()) {
                        entityManager.persist(newAuthor);
                        authors.add(newAuthor);
                    } else {
                        // because of the unique key restraint, always only one entity should be found
                        authors.add((Author) existingAuthors.toArray()[0]);
                    }
                }
                book.setAuthors(authors);
            }

            if (book.coverRequested()) {
                // for now just use the very first image that is found
                // also check for null-safety
                byte[] coverBytes = client.getEdition().getCoverImagesImages()[0];
                Cover cover = new Cover(coverBytes);
                coverRepository.save(cover);

                book.setCover(cover);
            }
        }
    }
    
    @HandleAfterDelete
    public void cleanupAuthors(Book book) {
        // delete all authors of the deleted book, that don't have any more writtenBooks 
        for (Author author : book.getAuthors()) {
            if (author.getBooks() == null || author.getBooks().isEmpty()) {
                authorRepository.deleteById(author.getId());
            }
        }
    }

}
