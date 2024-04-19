package com.poglibrary.db_conn.model.projection;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poglibrary.db_conn.model.Book;

@Projection(name = "bookProjection", types = { Book.class })
public interface BookProjection {

    String getIsbn();

    String getTitle();

    @JsonProperty("authors")
    Set<String> getAuthorsFormal();

    @JsonProperty("borrower")
    String getBorrowerFormal();
}
