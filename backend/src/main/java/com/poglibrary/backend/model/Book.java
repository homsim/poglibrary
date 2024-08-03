package com.poglibrary.backend.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {
    /*
     * ToDo:
     *  - GET on the entity ( /books/1 ) does not use the BookProjection
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "coverRequested")
    private Boolean coverRequested = false;

    @OneToOne
    @JoinColumn(name = "coverId")
    private Cover cover;

    @ManyToMany(targetEntity = com.poglibrary.backend.model.Author.class, fetch = FetchType.EAGER)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
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

    public Boolean coverRequested() { return this.coverRequested; }

    @Override
    public String toString() {
        // arbitrary
        return String.format(
                "Book[id=%d, title='%s', author(s)='%s', isbn=%s]",
                id, title, authors, isbn);
    }
}
