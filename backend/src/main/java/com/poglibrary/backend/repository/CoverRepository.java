package com.poglibrary.backend.repository;

import com.poglibrary.backend.model.Cover;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "covers", path = "covers")
public interface CoverRepository extends CrudRepository<Cover, Long> {
}
