package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.User;
import java.sql.*;

public class UserDAO {

    // Method to get a User by their ID
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Map the result set to a User object
                    return new User(
                            rs.getInt("userID"),
                            rs.getString("name")
                    );
                } else {
                    return null;  // User not found
                }
            }
        } catch (SQLException e) {
            // Log or rethrow the exception as needed
            throw new SQLException("Error fetching user with ID " + userId, e);
        }
    }

    // Method to insert a new User
    public boolean insertUser(User user) throws SQLException {
        String query = "INSERT INTO users (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Get the generated userID (assuming auto-increment field)
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserID(generatedKeys.getInt(1));  // Set the generated userID
                        return true;
                    }
                }
            }
            return false;  // Return false if insertion failed
        } catch (SQLException e) {
            // Log or rethrow the exception as needed
            throw new SQLException("Error inserting user", e);
        }
    }

    // Method to update a User's information (by userID)
    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET name = ? WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getUserID());  // Use userID for updating
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Return true if any row was updated
        } catch (SQLException e) {
            // Log or rethrow the exception as needed
            throw new SQLException("Error updating user with ID " + user.getUserID(), e);
        }
    }

    // Method to delete a User by userID
    public boolean deleteUser(int userID) throws SQLException {
        String query = "DELETE FROM users WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);  // Use userID for deleting
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Return true if at least one row was deleted
        } catch (SQLException e) {
            // Log or rethrow the exception as needed
            throw new SQLException("Error deleting user with ID " + userID, e);
        }
    }
}
