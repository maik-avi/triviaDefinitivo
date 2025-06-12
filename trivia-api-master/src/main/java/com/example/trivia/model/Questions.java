package com.example.trivia.model;

import java.util.Arrays;
import java.util.List;

public class Questions {

    private Integer id;
    private Integer roundId;
    private String type;
    private Integer difficulty;
    private String mediaUrl;

    //Array a = resultSet.getArray("options");
    //String[] s = a.getArray();
    //List<String> options = new ArrayList<>(Array.asList(s));


    // statement.setArray(1, con.createArrayOf("TEXT", opts.toArray))

    private List<String> options;
    private List<String> correctAnswers;

    public Questions() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
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
}
