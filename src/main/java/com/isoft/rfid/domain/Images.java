package com.isoft.rfid.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Images (images) entity.\n@author Ibrahim Mohamed.
 */
@Document(collection = "images")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("guid")
    private String guid;

    @NotNull
    @Field("cmd")
    private String cmd;

    @NotNull
    @Field("gantry")
    private Integer gantry;

    @Field("base_64")
    private byte[] base64;

    @Field("base_64_content_type")
    private String base64ContentType;

    @Field("creation_date")
    private String creationDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Images id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return this.guid;
    }

    public Images guid(String guid) {
        this.setGuid(guid);
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCmd() {
        return this.cmd;
    }

    public Images cmd(String cmd) {
        this.setCmd(cmd);
        return this;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getGantry() {
        return this.gantry;
    }

    public Images gantry(Integer gantry) {
        this.setGantry(gantry);
        return this;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public byte[] getBase64() {
        return this.base64;
    }

    public Images base64(byte[] base64) {
        this.setBase64(base64);
        return this;
    }

    public void setBase64(byte[] base64) {
        this.base64 = base64;
    }

    public String getBase64ContentType() {
        return this.base64ContentType;
    }

    public Images base64ContentType(String base64ContentType) {
        this.base64ContentType = base64ContentType;
        return this;
    }

    public void setBase64ContentType(String base64ContentType) {
        this.base64ContentType = base64ContentType;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public Images creationDate(String creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Images)) {
            return false;
        }
        return id != null && id.equals(((Images) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Images{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", cmd='" + getCmd() + "'" +
            ", gantry=" + getGantry() +
            ", base64='" + getBase64() + "'" +
            ", base64ContentType='" + getBase64ContentType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
