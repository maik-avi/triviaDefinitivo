package com.example.trivia.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoundQuestionDAO {
    private final Connection connection;

    public RoundQuestionDAO(Connection connection) {
        this.connection = connection;
    }

    public void addQuestionToRound(int roundId, int questionId) {
        String sql = "INSERT INTO round_questions (round_id, question_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roundId);
            statement.setInt(2, questionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeQuestionFromRound(int roundId, int questionId) {
        String sql = "DELETE FROM round_questions WHERE round_id = ? AND question_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roundId);
            statement.setInt(2, questionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> findQuestionIdsByRoundId(int roundId) {
        List<Integer> questionIds = new ArrayList<>();
        String sql = "SELECT question_id FROM round_questions WHERE round_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roundId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    questionIds.add(rs.getInt("question_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionIds;
    }

    public List<Integer> findRoundIdsByQuestionId(int questionId) {
        List<Integer> roundIds = new ArrayList<>();
        String sql = "SELECT round_id FROM round_questions WHERE question_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    roundIds.add(rs.getInt("round_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roundIds;
    }
}
