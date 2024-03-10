package com.poglibrary.db_conn.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // not sure if needed
@Entity
public class Author extends Person {
    @ManyToMany(mappedBy = "authors")
    Set<Book> writtenBooks = new HashSet<>();
}