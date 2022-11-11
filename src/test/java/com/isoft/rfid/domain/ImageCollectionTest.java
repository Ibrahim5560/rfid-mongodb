package com.isoft.rfid.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.rfid.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImageCollectionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageCollection.class);
        ImageCollection imageCollection1 = new ImageCollection();
        imageCollection1.setId("id1");
        ImageCollection imageCollection2 = new ImageCollection();
        imageCollection2.setId(imageCollection1.getId());
        assertThat(imageCollection1).isEqualTo(imageCollection2);
        imageCollection2.setId("id2");
        assertThat(imageCollection1).isNotEqualTo(imageCollection2);
        imageCollection1.setId(null);
        assertThat(imageCollection1).isNotEqualTo(imageCollection2);
    }
}
