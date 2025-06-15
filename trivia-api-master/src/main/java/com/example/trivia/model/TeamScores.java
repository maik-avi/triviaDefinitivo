package com.example.trivia.model;

public class TeamScores {

    private Integer teamId;
    private Integer gameId;
    private Integer points;

    public TeamScores() {
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "TeamScore{" +
                "teamId=" + teamId +
                ", gameId=" + gameId +
                ", points=" + points +
                '}';
    }
}
