package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Rental;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;

import java.sql.*;
import java.util.Date;

public class RentalDAO {

    // Get a rental by its ID
    public Rental getRentalById(int rentalId) throws SQLException {
        String query = "SELECT * FROM rentals WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Fetch user and vehicle by ID instead of by name
                User user = getUserById(rs.getInt("user_id"));
                Vehicle vehicle = getVehicleById(rs.getInt("vehicle_id"));
                return new Rental(
                        rs.getInt("rental_id"),
                        user,
                        vehicle,
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getDouble("total_cost")
                );
            }
        }
        return null;
    }

    // Fetch user by ID (helper method)
    private User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("user_name"));
            }
        }
        return null;
    }

    // Fetch vehicle by ID (helper method)
    private Vehicle getVehicleById(int vehicleId) throws SQLException {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("model"),
                        rs.getString("license_plate"),
                        rs.getDouble("price_per_day")
                );
            }
        }
        return null;
    }

    // Add a new rental
    public boolean addRental(Rental rental) throws SQLException {
        // Calculate total cost based on start date, end date, and vehicle price
        long duration = rental.getEndDate().getTime() - rental.getStartDate().getTime();
        long days = duration / (1000 * 60 * 60 * 24);  // Convert milliseconds to days
        double totalCost = days * rental.getVehicle().getPricePerDay();

        String query = "INSERT INTO rentals (user_id, vehicle_id, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rental.getUser().getUserID());  // Using userId, not userName
            stmt.setInt(2, rental.getVehicle().getVehicleId());
            stmt.setDate(3, new java.sql.Date(rental.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(rental.getEndDate().getTime()));
            stmt.setDouble(5, totalCost);  // Use calculated total cost
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the rental was added successfully
        }
    }

    // Update rental details
    public boolean updateRental(Rental rental) throws SQLException {
        // Calculate total cost again when updating
        long duration = rental.getEndDate().getTime() - rental.getStartDate().getTime();
        long days = duration / (1000 * 60 * 60 * 24);
        double totalCost = days * rental.getVehicle().getPricePerDay();

        String query = "UPDATE rentals SET start_date = ?, end_date = ?, total_cost = ? WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(rental.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(rental.getEndDate().getTime()));
            stmt.setDouble(3, totalCost);
            stmt.setInt(4, rental.getRentalId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete rental by ID
    public boolean deleteRental(int rentalId) throws SQLException {
        String query = "DELETE FROM rentals WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
