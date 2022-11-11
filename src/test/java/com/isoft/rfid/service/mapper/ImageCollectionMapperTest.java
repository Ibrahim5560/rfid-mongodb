package com.isoft.rfid.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageCollectionMapperTest {

    private ImageCollectionMapper imageCollectionMapper;

    @BeforeEach
    public void setUp() {
        imageCollectionMapper = new ImageCollectionMapperImpl();
    }
}
