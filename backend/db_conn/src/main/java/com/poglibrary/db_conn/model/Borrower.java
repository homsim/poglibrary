package com.poglibrary.db_conn.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Borrower extends Person {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "borrower")
    Set<Book> borrowedBooks = new HashSet<>();

    public Borrower(String name) {
        super(name);
    }
}