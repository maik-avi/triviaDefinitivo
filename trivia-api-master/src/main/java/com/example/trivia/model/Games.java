package com.example.trivia.model;

import java.time.OffsetDateTime;

public class Games {

    private Integer id;
    private Integer roomId;
    private Integer rounds;
    private Integer questionsPerRound;
    private Short difficulty;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;

    public Games() {
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
        return "Games{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", rounds=" + rounds +
                ", questionsPerRound=" + questionsPerRound +
                ", difficulty=" + difficulty +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                '}';
    }
}
