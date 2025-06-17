package com.example.trivia.model;

import java.time.OffsetDateTime;

public class Room {

    private Integer roomId;
    private String url;
    private OffsetDateTime createdAt;
    private String code;

    public Room() {
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setHostId(int i) {
    }
}
