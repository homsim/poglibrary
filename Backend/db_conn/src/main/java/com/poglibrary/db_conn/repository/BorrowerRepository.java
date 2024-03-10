package com.poglibrary.db_conn.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.poglibrary.db_conn.model.Borrower;


@RepositoryRestResource(collectionResourceRel = "borrowers", path = "borrowers")
public interface BorrowerRepository extends CrudRepository<Borrower, Long> {
    Borrower findById(long id);
    
    List<Borrower> findByLastname(@Param("lastname") String lastname);
    List<Borrower> findByFirstname(@Param("firstname") String firstname);
}