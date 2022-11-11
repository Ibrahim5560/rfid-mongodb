package com.isoft.rfid.service.impl;

import com.isoft.rfid.domain.ImageCollection;
import com.isoft.rfid.repository.ImageCollectionRepository;
import com.isoft.rfid.service.ImageCollectionService;
import com.isoft.rfid.service.dto.ImageCollectionDTO;
import com.isoft.rfid.service.mapper.ImageCollectionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ImageCollection}.
 */
@Service
public class ImageCollectionServiceImpl implements ImageCollectionService {

    private final Logger log = LoggerFactory.getLogger(ImageCollectionServiceImpl.class);

    private final ImageCollectionRepository imageCollectionRepository;

    private final ImageCollectionMapper imageCollectionMapper;

    public ImageCollectionServiceImpl(ImageCollectionRepository imageCollectionRepository, ImageCollectionMapper imageCollectionMapper) {
        this.imageCollectionRepository = imageCollectionRepository;
        this.imageCollectionMapper = imageCollectionMapper;
    }

    @Override
    public ImageCollectionDTO save(ImageCollectionDTO imageCollectionDTO) {
        log.debug("Request to save ImageCollection : {}", imageCollectionDTO);
        ImageCollection imageCollection = imageCollectionMapper.toEntity(imageCollectionDTO);
        imageCollection = imageCollectionRepository.save(imageCollection);
        return imageCollectionMapper.toDto(imageCollection);
    }

    @Override
    public ImageCollectionDTO update(ImageCollectionDTO imageCollectionDTO) {
        log.debug("Request to update ImageCollection : {}", imageCollectionDTO);
        ImageCollection imageCollection = imageCollectionMapper.toEntity(imageCollectionDTO);
        imageCollection = imageCollectionRepository.save(imageCollection);
        return imageCollectionMapper.toDto(imageCollection);
    }

    @Override
    public Optional<ImageCollectionDTO> partialUpdate(ImageCollectionDTO imageCollectionDTO) {
        log.debug("Request to partially update ImageCollection : {}", imageCollectionDTO);

        return imageCollectionRepository
            .findById(imageCollectionDTO.getId())
            .map(existingImageCollection -> {
                imageCollectionMapper.partialUpdate(existingImageCollection, imageCollectionDTO);

                return existingImageCollection;
            })
            .map(imageCollectionRepository::save)
            .map(imageCollectionMapper::toDto);
    }

    @Override
    public Page<ImageCollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImageCollections");
        return imageCollectionRepository.findAll(pageable).map(imageCollectionMapper::toDto);
    }

    @Override
    public Optional<ImageCollectionDTO> findOne(String id) {
        log.debug("Request to get ImageCollection : {}", id);
        return imageCollectionRepository.findById(id).map(imageCollectionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ImageCollection : {}", id);
        imageCollectionRepository.deleteById(id);
    }
}
