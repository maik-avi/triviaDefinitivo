package com.example.trivia.model;

public class Player {

    private Integer playerId;
    private Integer roomId;
    private String username;
    private Boolean isHost;


    //Esto es para que la aplicacion funcione, ya que me da errores, PERO NO SE HA DE TOMAR EN CUENTA
    private Long teamId;

    public Player() {
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsHost() {
        return isHost;
    }

    public void setIsHost(Boolean isHost) {
        this.isHost = isHost;
    }


    //NO SE HA DE TOMAR EN CUENTA, ES PARA QUE FUNCIONE EL SPRINGBOOT
    public Long getTeamId() {
        return teamId;
    }


    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", roomId=" + roomId +
                ", username='" + username + '\'' +
                ", isHost=" + isHost +
                '}';
    }
}
