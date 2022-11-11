package com.isoft.rfid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.rfid.IntegrationTest;
import com.isoft.rfid.domain.ImageCollection;
import com.isoft.rfid.repository.ImageCollectionRepository;
import com.isoft.rfid.service.dto.ImageCollectionDTO;
import com.isoft.rfid.service.mapper.ImageCollectionMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ImageCollectionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImageCollectionResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_CMD = "AAAAAAAAAA";
    private static final String UPDATED_CMD = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BASE_64 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BASE_64 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BASE_64_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BASE_64_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_GANTRY = 1;
    private static final Integer UPDATED_GANTRY = 2;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/image-collections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ImageCollectionRepository imageCollectionRepository;

    @Autowired
    private ImageCollectionMapper imageCollectionMapper;

    @Autowired
    private MockMvc restImageCollectionMockMvc;

    private ImageCollection imageCollection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageCollection createEntity() {
        ImageCollection imageCollection = new ImageCollection()
            .guid(DEFAULT_GUID)
            .cmd(DEFAULT_CMD)
            .base64(DEFAULT_BASE_64)
            .base64ContentType(DEFAULT_BASE_64_CONTENT_TYPE)
            .gantry(DEFAULT_GANTRY)
            .creationDate(DEFAULT_CREATION_DATE);
        return imageCollection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageCollection createUpdatedEntity() {
        ImageCollection imageCollection = new ImageCollection()
            .guid(UPDATED_GUID)
            .cmd(UPDATED_CMD)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE)
            .gantry(UPDATED_GANTRY)
            .creationDate(UPDATED_CREATION_DATE);
        return imageCollection;
    }

    @BeforeEach
    public void initTest() {
        imageCollectionRepository.deleteAll();
        imageCollection = createEntity();
    }

    @Test
    void createImageCollection() throws Exception {
        int databaseSizeBeforeCreate = imageCollectionRepository.findAll().size();
        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);
        restImageCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeCreate + 1);
        ImageCollection testImageCollection = imageCollectionList.get(imageCollectionList.size() - 1);
        assertThat(testImageCollection.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testImageCollection.getCmd()).isEqualTo(DEFAULT_CMD);
        assertThat(testImageCollection.getBase64()).isEqualTo(DEFAULT_BASE_64);
        assertThat(testImageCollection.getBase64ContentType()).isEqualTo(DEFAULT_BASE_64_CONTENT_TYPE);
        assertThat(testImageCollection.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testImageCollection.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    void createImageCollectionWithExistingId() throws Exception {
        // Create the ImageCollection with an existing ID
        imageCollection.setId("existing_id");
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        int databaseSizeBeforeCreate = imageCollectionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkGuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageCollectionRepository.findAll().size();
        // set the field null
        imageCollection.setGuid(null);

        // Create the ImageCollection, which fails.
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        restImageCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCmdIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageCollectionRepository.findAll().size();
        // set the field null
        imageCollection.setCmd(null);

        // Create the ImageCollection, which fails.
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        restImageCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkGantryIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageCollectionRepository.findAll().size();
        // set the field null
        imageCollection.setGantry(null);

        // Create the ImageCollection, which fails.
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        restImageCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllImageCollections() throws Exception {
        // Initialize the database
        imageCollectionRepository.save(imageCollection);

        // Get all the imageCollectionList
        restImageCollectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageCollection.getId())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].cmd").value(hasItem(DEFAULT_CMD)))
            .andExpect(jsonPath("$.[*].base64ContentType").value(hasItem(DEFAULT_BASE_64_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].base64").value(hasItem(Base64Utils.encodeToString(DEFAULT_BASE_64))))
            .andExpect(jsonPath("$.[*].gantry").value(hasItem(DEFAULT_GANTRY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    void getImageCollection() throws Exception {
        // Initialize the database
        imageCollectionRepository.save(imageCollection);

        // Get the imageCollection
        restImageCollectionMockMvc
            .perform(get(ENTITY_API_URL_ID, imageCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imageCollection.getId()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.cmd").value(DEFAULT_CMD))
            .andExpect(jsonPath("$.base64ContentType").value(DEFAULT_BASE_64_CONTENT_TYPE))
            .andExpect(jsonPath("$.base64").value(Base64Utils.encodeToString(DEFAULT_BASE_64)))
            .andExpect(jsonPath("$.gantry").value(DEFAULT_GANTRY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    void getNonExistingImageCollection() throws Exception {
        // Get the imageCollection
        restImageCollectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingImageCollection() throws Exception {
        // Initialize the database
        imageCollectionRepository.save(imageCollection);

        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();

        // Update the imageCollection
        ImageCollection updatedImageCollection = imageCollectionRepository.findById(imageCollection.getId()).get();
        updatedImageCollection
            .guid(UPDATED_GUID)
            .cmd(UPDATED_CMD)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE)
            .gantry(UPDATED_GANTRY)
            .creationDate(UPDATED_CREATION_DATE);
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(updatedImageCollection);

        restImageCollectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageCollectionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
        ImageCollection testImageCollection = imageCollectionList.get(imageCollectionList.size() - 1);
        assertThat(testImageCollection.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testImageCollection.getCmd()).isEqualTo(UPDATED_CMD);
        assertThat(testImageCollection.getBase64()).isEqualTo(UPDATED_BASE_64);
        assertThat(testImageCollection.getBase64ContentType()).isEqualTo(UPDATED_BASE_64_CONTENT_TYPE);
        assertThat(testImageCollection.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testImageCollection.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    void putNonExistingImageCollection() throws Exception {
        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();
        imageCollection.setId(UUID.randomUUID().toString());

        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageCollectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageCollectionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchImageCollection() throws Exception {
        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();
        imageCollection.setId(UUID.randomUUID().toString());

        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageCollectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamImageCollection() throws Exception {
        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();
        imageCollection.setId(UUID.randomUUID().toString());

        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageCollectionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateImageCollectionWithPatch() throws Exception {
        // Initialize the database
        imageCollectionRepository.save(imageCollection);

        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();

        // Update the imageCollection using partial update
        ImageCollection partialUpdatedImageCollection = new ImageCollection();
        partialUpdatedImageCollection.setId(imageCollection.getId());

        partialUpdatedImageCollection.cmd(UPDATED_CMD).creationDate(UPDATED_CREATION_DATE);

        restImageCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImageCollection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImageCollection))
            )
            .andExpect(status().isOk());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
        ImageCollection testImageCollection = imageCollectionList.get(imageCollectionList.size() - 1);
        assertThat(testImageCollection.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testImageCollection.getCmd()).isEqualTo(UPDATED_CMD);
        assertThat(testImageCollection.getBase64()).isEqualTo(DEFAULT_BASE_64);
        assertThat(testImageCollection.getBase64ContentType()).isEqualTo(DEFAULT_BASE_64_CONTENT_TYPE);
        assertThat(testImageCollection.getGantry()).isEqualTo(DEFAULT_GANTRY);
        assertThat(testImageCollection.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    void fullUpdateImageCollectionWithPatch() throws Exception {
        // Initialize the database
        imageCollectionRepository.save(imageCollection);

        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();

        // Update the imageCollection using partial update
        ImageCollection partialUpdatedImageCollection = new ImageCollection();
        partialUpdatedImageCollection.setId(imageCollection.getId());

        partialUpdatedImageCollection
            .guid(UPDATED_GUID)
            .cmd(UPDATED_CMD)
            .base64(UPDATED_BASE_64)
            .base64ContentType(UPDATED_BASE_64_CONTENT_TYPE)
            .gantry(UPDATED_GANTRY)
            .creationDate(UPDATED_CREATION_DATE);

        restImageCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImageCollection.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImageCollection))
            )
            .andExpect(status().isOk());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
        ImageCollection testImageCollection = imageCollectionList.get(imageCollectionList.size() - 1);
        assertThat(testImageCollection.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testImageCollection.getCmd()).isEqualTo(UPDATED_CMD);
        assertThat(testImageCollection.getBase64()).isEqualTo(UPDATED_BASE_64);
        assertThat(testImageCollection.getBase64ContentType()).isEqualTo(UPDATED_BASE_64_CONTENT_TYPE);
        assertThat(testImageCollection.getGantry()).isEqualTo(UPDATED_GANTRY);
        assertThat(testImageCollection.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    void patchNonExistingImageCollection() throws Exception {
        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();
        imageCollection.setId(UUID.randomUUID().toString());

        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imageCollectionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchImageCollection() throws Exception {
        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();
        imageCollection.setId(UUID.randomUUID().toString());

        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamImageCollection() throws Exception {
        int databaseSizeBeforeUpdate = imageCollectionRepository.findAll().size();
        imageCollection.setId(UUID.randomUUID().toString());

        // Create the ImageCollection
        ImageCollectionDTO imageCollectionDTO = imageCollectionMapper.toDto(imageCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageCollectionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImageCollection in the database
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteImageCollection() throws Exception {
        // Initialize the database
        imageCollectionRepository.save(imageCollection);

        int databaseSizeBeforeDelete = imageCollectionRepository.findAll().size();

        // Delete the imageCollection
        restImageCollectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, imageCollection.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImageCollection> imageCollectionList = imageCollectionRepository.findAll();
        assertThat(imageCollectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
