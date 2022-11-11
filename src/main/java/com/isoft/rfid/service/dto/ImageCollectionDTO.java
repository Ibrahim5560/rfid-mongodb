package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.ImageCollection} entity.
 */
@Schema(description = "ImageCollection (imageCollection) entity.\n@author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageCollectionDTO implements Serializable {

    private String id;

    @NotNull
    private String guid;

    @NotNull
    private String cmd;

    private byte[] base64;

    private String base64ContentType;

    @NotNull
    private Integer gantry;

    private Instant creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public byte[] getBase64() {
        return base64;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }

    public String getBase64ContentType() {
        return base64ContentType;
    }

    public void setBase64ContentType(String base64ContentType) {
        this.base64ContentType = base64ContentType;
    }

    public Integer getGantry() {
        return gantry;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageCollectionDTO)) {
            return false;
        }

        ImageCollectionDTO imageCollectionDTO = (ImageCollectionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imageCollectionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageCollectionDTO{" +
            "id='" + getId() + "'" +
            ", guid='" + getGuid() + "'" +
            ", cmd='" + getCmd() + "'" +
            ", base64='" + getBase64() + "'" +
            ", gantry=" + getGantry() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
