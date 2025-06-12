package com.example.trivia.model;



public class Teams {

    private Integer id;
    private Integer roomId;
    private String name;

    public Teams() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", name='" + name + '\'' +
                '}';
    }

}
