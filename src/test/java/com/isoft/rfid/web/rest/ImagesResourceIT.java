package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.Images;
import com.isoft.rfid.repository.ImagesRepository;
import com.isoft.rfid.service.dto.ImagesDTO;
import com.isoft.rfid.service.mapper.ImagesMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ImagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImagesResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_CMD = "AAAAAAAAAA";
    private static final String UPDATED_CMD = "BBBBBBBBBB";

    private static final Integer DEFAULT_GANTRY = 1;
    private static final Integer UPDATED_GANTRY = 2;

    private static final byte[] DEFAULT_BASE_64 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BASE_64 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BASE_64_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BASE_64_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CREATION_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private MockMvc restImagesMockMvc;

    private Images images;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Images createEntity() {
        Images images = new Images()
            .guid(DEFAULT_GUID)
            .cmd(DEFAULT_CMD)
            .gantry(DEFAULT_GANTRY)
            .base64(DEFAULT_BASE_64)
            .base64ContentType(DEFAULT_BASE_64_CONTENT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE);
        return images;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Images createUpdatedEntity() {
        Images images = new Images()
            .guid(UPDATED_GUID)
            .cmd(UPDATED_CMD)
            .gantry(UPDATED_GANTRY)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE);
        return images;
    }

    @BeforeEach
    public void initTest() {
        imagesRepository.deleteAll();
        images = createEntity();
    }

    @Test
    void createImages() throws Exception {
        int databaseSizeBeforeCreate = imagesRepository.findAll().size();
        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);
        restImagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate + 1);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testImages.getCmd()).isEqualTo(DEFAULT_CMD);
        assertThat(testImages.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testImages.getBase64()).isEqualTo(DEFAULT_BASE_64);
        assertThat(testImages.getBase64ContentType()).isEqualTo(DEFAULT_BASE_64_CONTENT_TYPE);
        assertThat(testImages.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    void createImagesWithExistingId() throws Exception {
        // Create the Images with an existing ID
        images.setId("existing_id");
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        int databaseSizeBeforeCreate = imagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkGuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagesRepository.findAll().size();
        // set the field null
        images.setGuid(null);

        // Create the Images, which fails.
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        restImagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagesRepository.findAll().size();
        // set the field null
        images.setCmd(null);

        // Create the Images, which fails.
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        restImagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkGantryIsRequired() throws Exception {
        int databaseSizeBeforeTest = imagesRepository.findAll().size();
        // set the field null
        images.setGantry(null);

        // Create the Images, which fails.
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        restImagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isBadRequest());

        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllImages() throws Exception {
        // Initialize the database
        imagesRepository.save(images);

        // Get all the imagesList
        restImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(images.getId())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].cmd").value(hasItem(DEFAULT_CMD)))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].base64ContentType").value(hasItem(DEFAULT_BASE_64_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].base64").value(hasItem(Base64Utils.encodeToString(DEFAULT_BASE_64))))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE)));
    }

    @Test
    void getImages() throws Exception {
        // Initialize the database
        imagesRepository.save(images);

        // Get the images
        restImagesMockMvc
            .perform(get(ENTITY_API_URL_ID, images.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(images.getId()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.cmd").value(DEFAULT_CMD))
            .andExpect(jsonPath("$.gantry").value(DEFAULT_GANTRY))
            .andExpect(jsonPath("$.base64ContentType").value(DEFAULT_BASE_64_CONTENT_TYPE))
            .andExpect(jsonPath("$.base64").value(Base64Utils.encodeToString(DEFAULT_BASE_64)))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE));
    }

    @Test
    void getNonExistingImages() throws Exception {
        // Get the images
        restImagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingImages() throws Exception {
        // Initialize the database
        imagesRepository.save(images);

        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images
        Images updatedImages = imagesRepository.findById(images.getId()).get();
        updatedImages
            .guid(UPDATED_GUID)
            .cmd(UPDATED_CMD)
            .gantry(UPDATED_GANTRY)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE);
        ImagesDTO imagesDTO = imagesMapper.toDto(updatedImages);

        restImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testImages.getCmd()).isEqualTo(UPDATED_CMD);
        assertThat(testImages.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testImages.getBase64()).isEqualTo(UPDATED_BASE_64);
        assertThat(testImages.getBase64ContentType()).isEqualTo(UPDATED_BASE_64_CONTENT_TYPE);
        assertThat(testImages.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    void putNonExistingImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();
        images.setId(UUID.randomUUID().toString());

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();
        images.setId(UUID.randomUUID().toString());

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();
        images.setId(UUID.randomUUID().toString());

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imagesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateImagesWithPatch() throws Exception {
        // Initialize the database
        imagesRepository.save(images);

        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images using partial update
        Images partialUpdatedImages = new Images();
        partialUpdatedImages.setId(images.getId());

        partialUpdatedImages
            .cmd(UPDATED_CMD)
            .gantry(UPDATED_GANTRY)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE);

        restImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImages))
            )
            .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testImages.getCmd()).isEqualTo(UPDATED_CMD);
        assertThat(testImages.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testImages.getBase64()).isEqualTo(UPDATED_BASE_64);
        assertThat(testImages.getBase64ContentType()).isEqualTo(UPDATED_BASE_64_CONTENT_TYPE);
        assertThat(testImages.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    void fullUpdateImagesWithPatch() throws Exception {
        // Initialize the database
        imagesRepository.save(images);

        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();

        // Update the images using partial update
        Images partialUpdatedImages = new Images();
        partialUpdatedImages.setId(images.getId());

        partialUpdatedImages
            .guid(UPDATED_GUID)
            .cmd(UPDATED_CMD)
            .gantry(UPDATED_GANTRY)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE)
            .creationDate(UPDATED_CREATION_DATE);

        restImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImages))
            )
            .andExpect(status().isOk());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
        Images testImages = imagesList.get(imagesList.size() - 1);
        assertThat(testImages.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testImages.getCmd()).isEqualTo(UPDATED_CMD);
        assertThat(testImages.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testImages.getBase64()).isEqualTo(UPDATED_BASE_64);
        assertThat(testImages.getBase64ContentType()).isEqualTo(UPDATED_BASE_64_CONTENT_TYPE);
        assertThat(testImages.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    void patchNonExistingImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();
        images.setId(UUID.randomUUID().toString());

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();
        images.setId(UUID.randomUUID().toString());

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamImages() throws Exception {
        int databaseSizeBeforeUpdate = imagesRepository.findAll().size();
        images.setId(UUID.randomUUID().toString());

        // Create the Images
        ImagesDTO imagesDTO = imagesMapper.toDto(images);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImagesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(imagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Images in the database
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteImages() throws Exception {
        // Initialize the database
        imagesRepository.save(images);

        int databaseSizeBeforeDelete = imagesRepository.findAll().size();

        // Delete the images
        restImagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, images.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Images> imagesList = imagesRepository.findAll();
        assertThat(imagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
