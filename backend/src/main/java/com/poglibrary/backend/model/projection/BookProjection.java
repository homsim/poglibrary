package com.poglibrary.backend.model.projection;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.poglibrary.backend.model.Author;
import com.poglibrary.backend.model.Book;
import com.poglibrary.backend.model.Borrower;


@Projection(name = "bookProjection", types = { Book.class })
public interface BookProjection {
    Long getId();

    String getIsbn();

    String getTitle();

    Set<Author> getAuthors();

    Borrower getBorrower();
}
