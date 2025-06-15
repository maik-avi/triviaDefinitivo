package com.example.trivia.model;

import java.util.List;

public class Questions {

    private Integer questionId;
    private String type; // multiple_choice, short_answer, buzzer
    private Short difficulty; // entre 1 y 3
    private String mediaUrl;
    private List<String> options;
    private List<String> correctAnswers;

    public Questions() {
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Short difficulty) {
        this.difficulty = difficulty;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "questionId=" + questionId +
                ", type='" + type + '\'' +
                ", difficulty=" + difficulty +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", options=" + options +
                ", correctAnswers=" + correctAnswers +
                '}';
    }
}
