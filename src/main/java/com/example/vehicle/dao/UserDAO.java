package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    // Get a User by their ID
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("name")  // Fetch user name
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching user with ID " + userId, e);
        }
    }

    // Insert a new User
    public boolean insertUser(User user) throws SQLException {
        String query = "INSERT INTO users (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserID(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            throw new SQLException("Error inserting user", e);
        }
    }

    // Update a User's information (by userID)
    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET name = ? WHERE user_id = ?";
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

    // Delete a User by userID
    public boolean deleteUser(int userID) throws SQLException {
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

    // Get all users
    public User[] getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            // Dynamically create a list of User objects
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("name")  // Fetch user name
                ));
            }
            return users.toArray(new User[0]);
        }
    }
}
