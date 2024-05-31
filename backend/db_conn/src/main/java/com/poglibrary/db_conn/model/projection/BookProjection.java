package com.poglibrary.db_conn.model.projection;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.poglibrary.db_conn.model.Author;
import com.poglibrary.db_conn.model.Book;
import com.poglibrary.db_conn.model.Borrower;


@Projection(name = "bookProjection", types = { Book.class })
public interface BookProjection {
    Long getId();

    String getIsbn();

    String getTitle();

    Set<Author> getAuthors();

    Borrower getBorrower();
}
