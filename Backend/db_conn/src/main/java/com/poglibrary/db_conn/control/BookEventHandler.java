package com.poglibrary.db_conn.control;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Service;

import com.poglibrary.db_conn.control.FetchBookInfo.OpenLibraryClient;
import com.poglibrary.db_conn.model.Author;
import com.poglibrary.db_conn.model.Book;
import com.poglibrary.db_conn.repository.AuthorRepository;
import com.poglibrary.db_conn.repository.BookRepository;

import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

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

    @HandleBeforeCreate
    public void beforeCreate(Book book) throws IOException, URISyntaxException {
        // format isbn
        String isbnFormatted = book.getIsbn().replace("-", "");
        book.setIsbn(isbnFormatted);

        /*
         * if only the isbn is given, get the other fields via Open Library API
         * ToDo: fallback scenario for another API if Open Library does not find the book
         */
        if (book.getAuthors() == null || book.getTitle() == null) {
            OpenLibraryClient client = new OpenLibraryClient(isbnFormatted);

            if (book.getTitle() == null) {
                book.setTitle(client.getTitle());
            }

            if (book.getAuthors() == null) {
                Set<Author> authors = new HashSet<Author>();
                for (String clientAuthor : client.getAuthors()) {
                    Author newAuthor = new Author(clientAuthor);

                    Set<Author> existingAuthors = authorRepository
                            .findByFirstnameAndLastname(newAuthor.getFirstname(),
                                    newAuthor.getLastname());

                    if (existingAuthors.size() == 0) {
                        entityManager.persist(newAuthor);
                        authors.add(newAuthor);
                    } else {
                        // because of the unique key restraint, always only one entity should be found
                        authors.add((Author) existingAuthors.toArray()[0]);
                    }
                }
                book.setAuthors(authors);
            }
        }
    }
}
