package com.example.trivia.DAO;

import com.example.trivia.model.Answer;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswerDAO {
    private final Connection connection;

    public AnswerDAO(Connection connection) {
        this.connection = connection;
    }

    public Answer save(Answer answer) throws SQLException {
        String sql;
        if (answer.getAnswerId() == null) {
            sql = "INSERT INTO answers (question_id, player_id, submitted_at, answer, correct, points_awarded) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE answers SET question_id = ?, player_id = ?, submitted_at = ?, answer = ?, correct = ?, points_awarded = ? " +
                    "WHERE answer_id = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, answer.getQuestionId());
            statement.setInt(2, answer.getPlayerId());
            statement.setObject(3, answer.getSubmittedAt());
            statement.setString(4, answer.getAnswer());
            statement.setBoolean(5, answer.getCorrect());
            statement.setInt(6, answer.getPointsAwarded());

            if (answer.getAnswerId() != null) {
                statement.setInt(7, answer.getAnswerId());
            }

            statement.executeUpdate();

            if (answer.getAnswerId() == null) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        answer.setAnswerId(generatedKeys.getInt(1));
                    }
                }
            }
        }

        return answer;
    }

    public Optional<Answer> findById(int id) throws SQLException {
        String sql = "SELECT * FROM answers WHERE answer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAnswer(rs));
                }
            }
        }
        return Optional.empty();
    }


    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM answers WHERE answer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Metodo auxiliar para convertir una fila del ResultSet en un objeto Answer.
    // Esto ayuda a evitar repetir código cada vez que leemos respuestas desde la base de datos.
    private Answer mapResultSetToAnswer(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setAnswerId(rs.getInt("answer_id"));
        answer.setQuestionId(rs.getInt("question_id"));
        answer.setPlayerId(rs.getInt("player_id"));
        answer.setSubmittedAt(rs.getObject("submitted_at", OffsetDateTime.class));
        answer.setAnswer(rs.getString("answer"));
        answer.setCorrect(rs.getBoolean("correct"));
        answer.setPointsAwarded(rs.getInt("points_awarded"));
        return answer;
    }
}
