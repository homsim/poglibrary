package com.poglibrary.db_conn.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.poglibrary.db_conn.model.Author;

@RepositoryRestResource(collectionResourceRel = "author", path = "author")
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findById(long id);
    Set<Author> findByLastname(@Param("lastname") String lastname);
    Set<Author> findByFirstname(@Param("firstname") String firstname);
    Set<Author> findByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);

    boolean existsByLastname(String lastname);
    boolean existsByFirstname(String firstname);
    boolean existsByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);
}
