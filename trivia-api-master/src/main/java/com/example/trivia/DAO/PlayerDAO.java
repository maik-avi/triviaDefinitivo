package com.example.trivia.DAO;

import com.example.trivia.model.Player;

import java.sql.*;
import java.util.Optional;

public class PlayerDAO {
    private final Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    public Player save(Player player) {
        if (player.getPlayerId() == null) {
            // INSERT
            String sql = "INSERT INTO players (room_id, username, is_host) VALUES (?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, player.getRoomId());
                statement.setString(2, player.getUsername());
                statement.setBoolean(3, player.getIsHost());

                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        player.setPlayerId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            String sql = "UPDATE players SET room_id = ?, username = ?, is_host = ? WHERE player_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, player.getRoomId());
                statement.setString(2, player.getUsername());
                statement.setBoolean(3, player.getIsHost());
                statement.setInt(4, player.getPlayerId());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return player;
    }

    public Optional<Player> findById(int id) {
        String sql = "SELECT * FROM players WHERE player_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Player player = new Player();
                    player.setPlayerId(rs.getInt("player_id"));
                    player.setRoomId(rs.getInt("room_id"));
                    player.setUsername(rs.getString("username"));
                    player.setIsHost(rs.getBoolean("is_host"));
                    return Optional.of(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM players WHERE player_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
