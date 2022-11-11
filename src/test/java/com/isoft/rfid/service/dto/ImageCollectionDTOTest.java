package com.isoft.rfid.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImageCollectionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageCollectionDTO.class);
        ImageCollectionDTO imageCollectionDTO1 = new ImageCollectionDTO();
        imageCollectionDTO1.setId("id1");
        ImageCollectionDTO imageCollectionDTO2 = new ImageCollectionDTO();
        assertThat(imageCollectionDTO1).isNotEqualTo(imageCollectionDTO2);
        imageCollectionDTO2.setId(imageCollectionDTO1.getId());
        assertThat(imageCollectionDTO1).isEqualTo(imageCollectionDTO2);
        imageCollectionDTO2.setId("id2");
        assertThat(imageCollectionDTO1).isNotEqualTo(imageCollectionDTO2);
        imageCollectionDTO1.setId(null);
        assertThat(imageCollectionDTO1).isNotEqualTo(imageCollectionDTO2);
    }
}
