package com.poglibrary.backend.repository;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.poglibrary.backend.model.Borrower;


@RepositoryRestResource(collectionResourceRel = "borrowers", path = "borrowers")
public interface BorrowerRepository extends CrudRepository<Borrower, Long> {
    Borrower findById(long id);
    
    Set<Borrower> findByLastname(@Param("lastname") String lastname);
    Set<Borrower> findByFirstname(@Param("firstname") String firstname);
}