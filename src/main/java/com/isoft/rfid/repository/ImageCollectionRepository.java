package com.isoft.rfid.repository;

import com.isoft.rfid.domain.ImageCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ImageCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageCollectionRepository extends MongoRepository<ImageCollection, String> {}
