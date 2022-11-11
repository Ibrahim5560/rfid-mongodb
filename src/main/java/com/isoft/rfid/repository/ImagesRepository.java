package com.isoft.rfid.repository;

import com.isoft.rfid.domain.Images;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Images entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagesRepository extends MongoRepository<Images, String> {}
