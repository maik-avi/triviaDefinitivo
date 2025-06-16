package com.example.trivia.model;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;

public class Round {

    private Integer roundId;
    private Integer gameId;
    private Integer roundNumber;
    private OffsetDateTime startedAt;
    private OffsetDateTime endedAt;

    public Round() {
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
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
        return "Rounds{" +
                "roundId=" + roundId +
                ", gameId=" + gameId +
                ", roundNumber=" + roundNumber +
                ", startedAt=" + startedAt +
                ", endedAt=" + endedAt +
                '}';
    }

}
