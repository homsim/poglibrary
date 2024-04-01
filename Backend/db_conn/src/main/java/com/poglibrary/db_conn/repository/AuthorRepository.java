package com.poglibrary.db_conn.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.poglibrary.db_conn.model.Author;


@RepositoryRestResource(collectionResourceRel = "author", path = "author")
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findById(long id);
    
    List<Author> findByLastname(@Param("lastname") String lastname);
    List<Author> findByFirstname(@Param("firstname") String firstname);
}
