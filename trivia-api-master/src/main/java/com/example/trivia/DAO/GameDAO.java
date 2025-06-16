package com.example.trivia.DAO;

import com.example.trivia.model.Game;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class GameDAO {
    private final Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;
    }

    public Game save(Game game) {
        String sql;
        boolean isInsert = game.getGameId() == null;

        if (isInsert) {
            sql = "INSERT INTO games (room_id, rounds, questions_per_round, difficulty, started_at, ended_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE games SET room_id = ?, rounds = ?, questions_per_round = ?, difficulty = ?, " +
                    "started_at = ?, ended_at = ? WHERE game_id = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, game.getRoomId());
            statement.setInt(2, game.getRounds());
            statement.setInt(3, game.getQuestionsPerRound());
            statement.setInt(4, game.getDifficulty());

            // Convertir OffsetDateTime a Timestamp
            statement.setTimestamp(5, game.getStartedAt() != null
                    ? Timestamp.from(game.getStartedAt().toInstant())
                    : null);
            statement.setTimestamp(6, game.getEndedAt() != null
                    ? Timestamp.from(game.getEndedAt().toInstant())
                    : null);

            if (!isInsert) {
                statement.setInt(7, game.getGameId());
            }

            statement.executeUpdate();

            if (isInsert) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        game.setGameId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    public Optional<Game> findById(int gameId) {
        String sql = "SELECT * FROM games WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gameId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Game game = new Game();
                    game.setGameId(rs.getInt("game_id"));
                    game.setRoomId(rs.getInt("room_id"));
                    game.setRounds(rs.getInt("rounds"));
                    game.setQuestionsPerRound(rs.getInt("questions_per_round"));
                    game.setDifficulty((short) rs.getInt("difficulty"));

// Transformacion de OffSetDAteTime a Timestamp
                    Timestamp started = rs.getTimestamp("started_at");
                    Timestamp ended = rs.getTimestamp("ended_at");

                    game.setStartedAt(started != null
                            ? started.toInstant().atOffset(ZoneOffset.UTC)
                            : null);
                    game.setEndedAt(ended != null
                            ? ended.toInstant().atOffset(ZoneOffset.UTC)
                            : null);

                    return Optional.of(game);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteById(int gameId) {
        String sql = "DELETE FROM games WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
