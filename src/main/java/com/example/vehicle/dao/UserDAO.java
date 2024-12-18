package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class UserDAO {
    public static User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name")
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching user with ID " + userId, e);
        }
    }
    public int insertUser(User user) throws SQLException {
        String query = "INSERT INTO users (user_name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            //ps.setInt(1, user.getUserID());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserID(generatedKeys.getInt(1));
                        return user.getUserID();
                    }
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new SQLException("Error inserting user", e);
        }
    }
    public   boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET user_name = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getUserID());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error updating user with ID " + user.getUserID(), e);
        }
    }
    public  boolean deleteUser(int userID) throws SQLException {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting user with ID " + userID, e);
        }
    }
    public  List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();

        String query = "SELECT u.user_id, u.user_name "
                + "FROM users u";
        List<String> userDetails = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int userid = rs.getInt("user_id");
                String username = rs.getString("user_name");
                users.add( new User(userid, username));
            }
        }
        return users;
    }

}

