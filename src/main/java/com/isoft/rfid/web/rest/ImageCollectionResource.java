package com.isoft.rfid.web.rest;

import com.isoft.rfid.repository.ImageCollectionRepository;
import com.isoft.rfid.service.ImageCollectionService;
import com.isoft.rfid.service.dto.ImageCollectionDTO;
import com.isoft.rfid.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.isoft.rfid.domain.ImageCollection}.
 */
@RestController
@RequestMapping("/api")
public class ImageCollectionResource {

    private final Logger log = LoggerFactory.getLogger(ImageCollectionResource.class);

    private static final String ENTITY_NAME = "imageCollection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageCollectionService imageCollectionService;

    private final ImageCollectionRepository imageCollectionRepository;

    public ImageCollectionResource(ImageCollectionService imageCollectionService, ImageCollectionRepository imageCollectionRepository) {
        this.imageCollectionService = imageCollectionService;
        this.imageCollectionRepository = imageCollectionRepository;
    }

    /**
     * {@code POST  /image-collections} : Create a new imageCollection.
     *
     * @param imageCollectionDTO the imageCollectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageCollectionDTO, or with status {@code 400 (Bad Request)} if the imageCollection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-collections")
    public ResponseEntity<ImageCollectionDTO> createImageCollection(@Valid @RequestBody ImageCollectionDTO imageCollectionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ImageCollection : {}", imageCollectionDTO);
        if (imageCollectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new imageCollection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageCollectionDTO result = imageCollectionService.save(imageCollectionDTO);
        return ResponseEntity
            .created(new URI("/api/image-collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /image-collections/:id} : Updates an existing imageCollection.
     *
     * @param id the id of the imageCollectionDTO to save.
     * @param imageCollectionDTO the imageCollectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageCollectionDTO,
     * or with status {@code 400 (Bad Request)} if the imageCollectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageCollectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-collections/{id}")
    public ResponseEntity<ImageCollectionDTO> updateImageCollection(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ImageCollectionDTO imageCollectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ImageCollection : {}, {}", id, imageCollectionDTO);
        if (imageCollectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageCollectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageCollectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImageCollectionDTO result = imageCollectionService.update(imageCollectionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageCollectionDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /image-collections/:id} : Partial updates given fields of an existing imageCollection, field will ignore if it is null
     *
     * @param id the id of the imageCollectionDTO to save.
     * @param imageCollectionDTO the imageCollectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageCollectionDTO,
     * or with status {@code 400 (Bad Request)} if the imageCollectionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the imageCollectionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the imageCollectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/image-collections/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImageCollectionDTO> partialUpdateImageCollection(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ImageCollectionDTO imageCollectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ImageCollection partially : {}, {}", id, imageCollectionDTO);
        if (imageCollectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageCollectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageCollectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImageCollectionDTO> result = imageCollectionService.partialUpdate(imageCollectionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageCollectionDTO.getId())
        );
    }

    /**
     * {@code GET  /image-collections} : get all the imageCollections.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageCollections in body.
     */
    @GetMapping("/image-collections")
    public ResponseEntity<List<ImageCollectionDTO>> getAllImageCollections(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ImageCollections");
        Page<ImageCollectionDTO> page = imageCollectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /image-collections/:id} : get the "id" imageCollection.
     *
     * @param id the id of the imageCollectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageCollectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-collections/{id}")
    public ResponseEntity<ImageCollectionDTO> getImageCollection(@PathVariable String id) {
        log.debug("REST request to get ImageCollection : {}", id);
        Optional<ImageCollectionDTO> imageCollectionDTO = imageCollectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageCollectionDTO);
    }

    /**
     * {@code DELETE  /image-collections/:id} : delete the "id" imageCollection.
     *
     * @param id the id of the imageCollectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-collections/{id}")
    public ResponseEntity<Void> deleteImageCollection(@PathVariable String id) {
        log.debug("REST request to delete ImageCollection : {}", id);
        imageCollectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
