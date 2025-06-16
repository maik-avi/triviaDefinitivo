package com.example.trivia.model;

public class Team {

    private Integer teamId;
    private Integer roomId;

    public Team() {
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
