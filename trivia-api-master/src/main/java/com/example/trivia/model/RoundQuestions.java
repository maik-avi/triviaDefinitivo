package com.example.trivia.model;

public class RoundQuestions {

    private Integer roundId;
    private Integer questionId;

    public RoundQuestions() {
    }

    public RoundQuestions(Integer roundId, Integer questionId) {
        this.roundId = roundId;
        this.questionId = questionId;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "RoundQuestions{" +
                "roundId=" + roundId +
                ", questionId=" + questionId +
                '}';
    }
}
