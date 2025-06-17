package com.example.trivia.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerTeamDAO {
    private final Connection connection;

    public PlayerTeamDAO(Connection connection) {
        this.connection = connection;
    }

    public void assignPlayerToTeam(int playerId, int teamId) {
        String sql = "INSERT INTO player_team (player_id, team_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playerId);
            statement.setInt(2, teamId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePlayerFromTeam(int playerId, int teamId) {
        String sql = "DELETE FROM player_team WHERE player_id = ? AND team_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playerId);
            statement.setInt(2, teamId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> findPlayerIdsByTeamId(int teamId) {
        List<Integer> playerIds = new ArrayList<>();
        String sql = "SELECT player_id FROM player_team WHERE team_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teamId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    playerIds.add(rs.getInt("player_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerIds;
    }

    public Integer findTeamIdByPlayerId(int playerId) {
        String sql = "SELECT team_id FROM player_team WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("team_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no está asignado
    }

    public boolean isPlayerAssigned(int playerId) {
        String sql = "SELECT 1 FROM player_team WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playerId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next(); // true si hay al menos un resultado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
