package com.poglibrary.db_conn.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "lastname", "firstname" })
})
public class Author extends Person {
    @ManyToMany(mappedBy = "authors")
    Set<Book> books = new HashSet<>();

    public Author(String name) {
        super(name);
    }
}