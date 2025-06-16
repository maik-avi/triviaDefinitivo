package com.example.trivia.DAO;

import com.example.trivia.model.Team;

import java.sql.*;
import java.util.Optional;

public class TeamDAO {
    private final Connection connection;

    public TeamDAO(Connection connection) {
        this.connection = connection;
    }

    public Team save(Team team) {
        if (team.getTeamId() == null) {
            // INSERT
            String sql = "INSERT INTO teams (room_id) VALUES (?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, team.getRoomId());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        team.setTeamId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            // UPDATE
            String sql = "UPDATE teams SET room_id = ? WHERE team_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, team.getRoomId());
                statement.setInt(2, team.getTeamId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return team;
    }

    public Optional<Team> findById(int id) {
        String sql = "SELECT * FROM teams WHERE team_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Team team = new Team();
                    team.setTeamId(rs.getInt("team_id"));
                    team.setRoomId(rs.getInt("room_id"));
                    return Optional.of(team);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM teams WHERE team_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
