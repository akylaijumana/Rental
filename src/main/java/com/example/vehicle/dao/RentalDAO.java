package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Rental;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;
import java.sql.*;

public class RentalDAO {

    // Get a rental by its ID
    public Rental getRentalById(int rentalId) throws SQLException {
        String query = "SELECT * FROM rentals WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("user_name"));  // Assuming user is identified by name
                Vehicle vehicle = new Vehicle(rs.getInt("vehicle_id"), rs.getString("model"), rs.getString("license_plate"), rs.getDouble("price_per_day"));
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

    // Add a new rental
    public boolean addRental(Rental rental) throws SQLException {
        String query = "INSERT INTO rentals (user_name, vehicle_id, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, rental.getUser().getName());
            stmt.setInt(2, rental.getVehicle().getVehicleId());
            stmt.setDate(3, new java.sql.Date(rental.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(rental.getEndDate().getTime()));
            stmt.setDouble(5, rental.getTotalCost());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the rental was added successfully
        }
    }

    // Update rental details
    public boolean updateRental(Rental rental) throws SQLException {
        String query = "UPDATE rentals SET start_date = ?, end_date = ?, total_cost = ? WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(rental.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(rental.getEndDate().getTime()));
            stmt.setDouble(3, rental.getTotalCost());
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
