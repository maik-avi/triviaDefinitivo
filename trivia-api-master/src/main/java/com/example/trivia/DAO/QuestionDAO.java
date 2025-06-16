package com.example.trivia.DAO;

import com.example.trivia.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QuestionDAO {
    private final Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }

    public Question save(Question question) {
        boolean isInsert = question.getQuestionId() == null;
        String sql;

        if (isInsert) {
            sql = "INSERT INTO questions (type, difficulty, media_url, options, correct_answers) " +
                    "VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE questions SET type = ?, difficulty = ?, media_url = ?, options = ?, correct_answers = ? " +
                    "WHERE question_id = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, question.getType());
            statement.setInt(2, question.getDifficulty());
            statement.setString(3, question.getMediaUrl());


            // Aqui está la conversion de comentó Artem

            Array optionsArray = connection.createArrayOf("TEXT", question.getOptions().toArray());
            statement.setArray(4, optionsArray);

            Array correctAnswersArray = connection.createArrayOf("TEXT", question.getCorrectAnswers().toArray());
            statement.setArray(5, correctAnswersArray);

            if (!isInsert) {
                statement.setInt(6, question.getQuestionId());
            }

            statement.executeUpdate();

            if (isInsert) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        question.setQuestionId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return question;
    }

    public Optional<Question> findById(int id) {
        String sql = "SELECT * FROM questions WHERE question_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Question q = new Question();
                    q.setQuestionId(rs.getInt("question_id"));
                    q.setType(rs.getString("type"));
                    q.setDifficulty((short) rs.getInt("difficulty"));
                    q.setMediaUrl(rs.getString("media_url"));


                    // Aqui está lo de el uso de Array List que Artem me mencionó, usando los de "options"
                    Array optionsArray = rs.getArray("options");
                    if (optionsArray != null) {
                        q.setOptions(Arrays.asList((String[]) optionsArray.getArray()));
                    } else {
                        q.setOptions(new ArrayList<>());
                    }

                    Array correctAnswersArray = rs.getArray("correct_answers");
                    if (correctAnswersArray != null) {
                        q.setCorrectAnswers(Arrays.asList((String[]) correctAnswersArray.getArray()));
                    } else {
                        q.setCorrectAnswers(new ArrayList<>());
                    }

                    return Optional.of(q);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM questions WHERE question_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
