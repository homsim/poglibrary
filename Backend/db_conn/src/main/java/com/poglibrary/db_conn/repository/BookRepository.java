package com.poglibrary.db_conn.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.poglibrary.db_conn.model.Book;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findById(long id);

    List<Book> findByTitle(@Param("title") String title);

    //List<Book> findByAuthors(@Param("authors") List<String> authors);

    List<Book> findByIsbn(@Param("isbn") String isbn);

}