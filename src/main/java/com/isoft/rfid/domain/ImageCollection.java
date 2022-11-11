package com.isoft.rfid.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * ImageCollection (imageCollection) entity.\n@author Ibrahim Mohamed.
 */
@Document(collection = "image_collection")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("guid")
    private String guid;

    @NotNull
    @Field("cmd")
    private String cmd;

    @Field("base_64")
    private byte[] base64;

    @Field("base_64_content_type")
    private String base64ContentType;

    @NotNull
    @Field("gantry")
    private Integer gantry;

    @Field("creation_date")
    private Instant creationDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ImageCollection id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return this.guid;
    }

    public ImageCollection guid(String guid) {
        this.setGuid(guid);
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCmd() {
        return this.cmd;
    }

    public ImageCollection cmd(String cmd) {
        this.setCmd(cmd);
        return this;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public byte[] getBase64() {
        return this.base64;
    }

    public ImageCollection base64(byte[] base64) {
        this.setBase64(base64);
        return this;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }

    public String getBase64ContentType() {
        return this.base64ContentType;
    }

    public ImageCollection base64ContentType(String base64ContentType) {
        this.base64ContentType = base64ContentType;
        return this;
    }

    public void setBase64ContentType(String base64ContentType) {
        this.base64ContentType = base64ContentType;
    }

    public Integer getGantry() {
        return this.gantry;
    }

    public ImageCollection gantry(Integer gantry) {
        this.setGantry(gantry);
        return this;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public ImageCollection creationDate(Instant creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageCollection)) {
            return false;
        }
        return id != null && id.equals(((ImageCollection) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageCollection{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", cmd='" + getCmd() + "'" +
            ", base64='" + getBase64() + "'" +
            ", base64ContentType='" + getBase64ContentType() + "'" +
            ", gantry=" + getGantry() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
