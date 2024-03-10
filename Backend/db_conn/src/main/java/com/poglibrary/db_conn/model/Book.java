package com.poglibrary.db_conn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String authors; // will be changed to user-defined type Person/Author
    private String isbn; // will be changed to user-defined type Isbn
    @Column(nullable = false)
    private boolean borrowed;   // make this such that it is false if borrower_id is null
    @Column(name = "borrowerId")
    private Long borrowerId;

    public Book(String title, String authors, String isbn) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.borrowed = false;
        this.borrowerId = null;
    }

    @Override
    public String toString() {
        // arbitrary
        return String.format(
                "Customer[id=%d, title='%s', author(s)='%s', isbn=%s]",
                id, title, authors, isbn);
    }
}
