package com.example.trivia.DAO;

import com.example.trivia.model.Rooms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomsDAO {
    private Connection connection;

    public RoomsDAO(Connection connection) {
        this.connection = connection;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM room WHERE room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
