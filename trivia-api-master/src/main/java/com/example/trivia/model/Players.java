package com.example.trivia.model;

public class Players {

    private Integer playerId;
    private Integer roomId;
    private String username;
    private Boolean isHost;

    public Players() {
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsHost() {
        return isHost;
    }

    public void setIsHost(Boolean isHost) {
        this.isHost = isHost;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", roomId=" + roomId +
                ", username='" + username + '\'' +
                ", isHost=" + isHost +
                '}';
    }
}
