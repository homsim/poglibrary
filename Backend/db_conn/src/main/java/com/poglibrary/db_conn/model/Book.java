package com.poglibrary.db_conn.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @ManyToMany(targetEntity = com.poglibrary.db_conn.model.Author.class, fetch = FetchType.EAGER)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "bookId"), inverseJoinColumns = @JoinColumn(name = "authorId"))
    private Set<Author> authors;

    @ManyToOne
    @JoinColumn(name = "borrowerId")
    private Borrower borrower;

    public Book(String title, Set<Author> authors, String isbn) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.borrower = null;
    }

    @Override
    public String toString() {
        // arbitrary
        return String.format(
                "Book[id=%d, title='%s', author(s)='%s', isbn=%s]",
                id, title, authors, isbn);
    }

    public Set<String> getAuthorsFormal() {
        Set<String> authors = new HashSet<String>();
        if (this.authors != null) {
            for (Author author : this.authors) {
                authors.add(author.getFormal());
            }
            return authors;
        } else {
            return new HashSet<String>();
        }
    }

    public String getBorrowerFormal() {
        if (this.borrower != null) {
            return this.borrower.getFormal();
        } else {
            return null;
        }
    }
}
