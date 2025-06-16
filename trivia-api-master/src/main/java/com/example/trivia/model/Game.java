package com.example.trivia.model;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

public class Game {

    private Integer gameId;
    private Integer roomId;
    private Integer rounds;
    private Integer questionsPerRound;
    private Short difficulty;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;

    public Game() {
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public Integer getQuestionsPerRound() {
        return questionsPerRound;
    }

    public void setQuestionsPerRound(Integer questionsPerRound) {
        this.questionsPerRound = questionsPerRound;
    }

    public Short getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Short difficulty) {
        this.difficulty = difficulty;
    }

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(OffsetDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public OffsetDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(OffsetDateTime endedAt) {
        this.endedAt = endedAt;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", roomId=" + roomId +
                ", rounds=" + rounds +
                ", questionsPerRound=" + questionsPerRound +
                ", difficulty=" + difficulty +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                '}';
    }
}