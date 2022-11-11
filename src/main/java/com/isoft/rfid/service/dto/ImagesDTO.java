package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.Images} entity.
 */
@Schema(description = "Images (images) entity.\n@author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImagesDTO implements Serializable {

    private String id;

    @NotNull
    private String guid;

    @NotNull
    private String cmd;

    @NotNull
    private Integer gantry;

    private byte[] base64;

    private String base64ContentType;
    private String creationDate;

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

    public Integer getGantry() {
        return gantry;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagesDTO)) {
            return false;
        }

        ImagesDTO imagesDTO = (ImagesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imagesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagesDTO{" +
            "id='" + getId() + "'" +
            ", guid='" + getGuid() + "'" +
            ", cmd='" + getCmd() + "'" +
            ", gantry=" + getGantry() +
            ", base64='" + getBase64() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
