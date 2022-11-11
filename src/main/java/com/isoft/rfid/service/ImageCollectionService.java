package com.isoft.rfid.service;

import com.isoft.rfid.service.dto.ImageCollectionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.rfid.domain.ImageCollection}.
 */
public interface ImageCollectionService {
    /**
     * Save a imageCollection.
     *
     * @param imageCollectionDTO the entity to save.
     * @return the persisted entity.
     */
    ImageCollectionDTO save(ImageCollectionDTO imageCollectionDTO);

    /**
     * Updates a imageCollection.
     *
     * @param imageCollectionDTO the entity to update.
     * @return the persisted entity.
     */
    ImageCollectionDTO update(ImageCollectionDTO imageCollectionDTO);

    /**
     * Partially updates a imageCollection.
     *
     * @param imageCollectionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImageCollectionDTO> partialUpdate(ImageCollectionDTO imageCollectionDTO);

    /**
     * Get all the imageCollections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageCollectionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" imageCollection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImageCollectionDTO> findOne(String id);

    /**
     * Delete the "id" imageCollection.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
