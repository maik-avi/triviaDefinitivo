package com.example.trivia.DAO;

import com.example.trivia.model.Round;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class RoundDAO {
    private final Connection connection;

    public RoundDAO(Connection connection) {
        this.connection = connection;
    }

    public Round save(Round round) {
        String sql;
        boolean isInsert = round.getRoundId() == null;

        if (isInsert) {
            sql = "INSERT INTO rounds (game_id, round_number, started_at, ended_at) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE rounds SET game_id = ?, round_number = ?, started_at = ?, ended_at = ? WHERE round_id = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, round.getGameId());
            statement.setInt(2, round.getRoundNumber());

            // Convertir OffsetDateTime a Timestamp
            statement.setTimestamp(3, round.getStartedAt() != null
                    ? Timestamp.from(round.getStartedAt().toInstant())
                    : null);
            statement.setTimestamp(4, round.getEndedAt() != null
                    ? Timestamp.from(round.getEndedAt().toInstant())
                    : null);

            if (!isInsert) {
                statement.setInt(5, round.getRoundId());
            }

            statement.executeUpdate();

            if (isInsert) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        round.setRoundId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return round;
    }

    public Optional<Round> findById(int roundId) {
        String sql = "SELECT * FROM rounds WHERE round_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roundId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Round round = new Round();
                    round.setRoundId(rs.getInt("round_id"));
                    round.setGameId(rs.getInt("game_id"));
                    round.setRoundNumber(rs.getInt("round_number"));

                    Timestamp started = rs.getTimestamp("started_at");
                    Timestamp ended = rs.getTimestamp("ended_at");

                    round.setStartedAt(started != null
                            ? started.toInstant().atOffset(ZoneOffset.UTC)
                            : null);
                    round.setEndedAt(ended != null
                            ? ended.toInstant().atOffset(ZoneOffset.UTC)
                            : null);

                    return Optional.of(round);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteById(int roundId) {
        String sql = "DELETE FROM rounds WHERE round_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roundId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
