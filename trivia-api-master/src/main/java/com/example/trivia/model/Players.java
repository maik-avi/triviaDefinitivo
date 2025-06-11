package com.example.trivia.model;


public class Players {

    private Integer id;
    private Integer roomId;
    private String username;
    private Boolean isHost;

    public Players() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                "id=" + id +
                ", roomId=" + roomId +
                ", username='" + username + '\'' +
                ", isHost=" + isHost +
                '}';
    }

}