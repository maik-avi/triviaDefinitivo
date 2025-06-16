package com.example.trivia.DAO;

import com.example.trivia.model.TeamScore;

import java.sql.*;
import java.util.Optional;

public class TeamScoreDAO {
    private Connection connection;

    public TeamScoreDAO(Connection connection) {
        this.connection = connection;
    }


    public TeamScore save(TeamScore teamScore) throws SQLException {
        String sql;
        if (exists(teamScore.getTeamId(), teamScore.getGameId())) {
            sql = "UPDATE team_scores SET points = ? WHERE team_id = ? AND game_id = ?";
        } else {
            sql = "INSERT INTO team_scores (team_id, game_id, points) VALUES (?, ?, ?)";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (sql.startsWith("UPDATE")) {
                statement.setInt(1, teamScore.getPoints());
                statement.setInt(2, teamScore.getTeamId());
                statement.setInt(3, teamScore.getGameId());
            } else {
                statement.setInt(1, teamScore.getTeamId());
                statement.setInt(2, teamScore.getGameId());
                statement.setInt(3, teamScore.getPoints());
            }
            statement.executeUpdate();
        }

        return teamScore;
    }


    public boolean exists(int teamId, int gameId) throws SQLException {
        String sql = "SELECT 1 FROM team_scores WHERE team_id = ? AND game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teamId);
            statement.setInt(2, gameId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }


    public Optional<TeamScore> findById(int teamId, int gameId) throws SQLException {
        String sql = "SELECT * FROM team_scores WHERE team_id = ? AND game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teamId);
            statement.setInt(2, gameId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    TeamScore ts = new TeamScore();
                    ts.setTeamId(rs.getInt("team_id"));
                    ts.setGameId(rs.getInt("game_id"));
                    ts.setPoints(rs.getInt("points"));
                    return Optional.of(ts);
                }
            }
        }
        return Optional.empty();
    }


    public void deleteById(int teamId, int gameId) throws SQLException {
        String sql = "DELETE FROM team_scores WHERE team_id = ? AND game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teamId);
            statement.setInt(2, gameId);
            statement.executeUpdate();
        }
    }
}
