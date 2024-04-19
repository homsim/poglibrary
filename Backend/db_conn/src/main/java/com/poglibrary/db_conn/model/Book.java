package com.poglibrary.db_conn.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "title")
    private String title;

    @ManyToMany(targetEntity = com.poglibrary.db_conn.model.Author.class)
    @JoinTable(name = "author_book", 
        joinColumns = @JoinColumn(name = "bookId"), 
        inverseJoinColumns = @JoinColumn(name = "authorId"))
    private Set<Author> authors; 

    @Column(name = "borrowerId")
    private Long borrowerId;

    public Book(String title, Set<Author> authors, String isbn) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
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
