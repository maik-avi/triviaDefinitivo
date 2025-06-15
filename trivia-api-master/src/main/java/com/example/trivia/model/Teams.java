package com.example.trivia.model;

public class Teams {

    private Integer teamId;
    private Integer roomId;

    public Teams() {
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", roomId=" + roomId +
                '}';
    }
}
