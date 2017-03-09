package io.ipoli.android.app.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.ipoli.android.app.utils.DateUtils;

/**
 * Created by Venelin Valkov <venelin@curiousily.com>
 * on 7/27/16.
 */
public abstract class PersistedObject {

    @JsonProperty(value = "_id")
    private String id;
    private String type;
    private Long createdAt;
    private Long updatedAt;
    private String playerId;

    public PersistedObject(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void markUpdated() {
        setUpdatedAt(DateUtils.nowUTC().getTime());
    }
}
