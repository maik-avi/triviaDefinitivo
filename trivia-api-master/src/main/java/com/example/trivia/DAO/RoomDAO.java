package com.example.trivia.DAO;

import com.example.trivia.model.Room;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.Optional;

public class RoomDAO {
    private final Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    public Room save(Room room) {
        if (room.getRoomId() == null) {

            String sql = "INSERT INTO rooms (url, created_at) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, room.getUrl());
                statement.setObject(2, room.getCreatedAt());

                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        room.setRoomId(generatedKeys.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            // UPDATE
            String sql = "UPDATE rooms SET url = ?, created_at = ? WHERE room_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, room.getUrl());
                statement.setObject(2, room.getCreatedAt());
                statement.setInt(3, room.getRoomId());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return room;
    }

    public Optional<Room> findById(int id) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Room room = new Room();
                    room.setRoomId(resultSet.getInt("room_id"));
                    room.setUrl(resultSet.getString("url"));
                    room.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    return Optional.of(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Room> findByUrl(String url) {
        String sql = "SELECT * FROM rooms WHERE url = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, url);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Room room = new Room();
                    room.setRoomId(resultSet.getInt("room_id"));
                    room.setUrl(resultSet.getString("url"));
                    room.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    return Optional.of(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
