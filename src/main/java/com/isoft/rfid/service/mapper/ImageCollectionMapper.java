package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.ImageCollection;
import com.isoft.rfid.service.dto.ImageCollectionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImageCollection} and its DTO {@link ImageCollectionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageCollectionMapper extends EntityMapper<ImageCollectionDTO, ImageCollection> {}
