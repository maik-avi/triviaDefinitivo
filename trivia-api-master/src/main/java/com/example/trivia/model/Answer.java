package com.example.trivia.model;

import java.time.OffsetDateTime;

public class Answer {

    private Integer answerId;
    private Integer questionId;
    private Integer playerId;
    private OffsetDateTime submittedAt;
    private String answer;
    private Boolean correct;
    private Integer pointsAwarded;

    public Answer() {
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(OffsetDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Integer getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(Integer pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", questionId=" + questionId +
                ", playerId=" + playerId +
                ", submittedAt=" + submittedAt +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                ", pointsAwarded=" + pointsAwarded +
                '}';
    }
}
