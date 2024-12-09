package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Rental;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    // Retrieve a rental by its ID
    public static Rental getRentalById(int rentalId) throws SQLException {
        String query = "SELECT r.rental_id, r.user_id, u.user_name, r.vehicle_id, v.type_name, v.license_plate, v.price_per_day, r.start_date, r.end_date, r.total_cost " +
                "FROM rentals r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN vehicles v ON r.vehicle_id = v.vehicle_id " +
                "WHERE r.rental_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getInt("user_id"), rs.getString("user_name"));
                    Vehicle vehicle = new Vehicle(
                            rs.getInt("vehicle_id"),
                            rs.getString("type_name"),
                            rs.getString("license_plate"),
                            rs.getDouble("price_per_day")
                    );
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
        }
        return null; // If no rental found
    }

    // Add a new rental
    public static  int addRental(Rental rental) throws SQLException {
        String query = "INSERT INTO rentals (user_id, vehicle_id, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rental.getUser().getUserID()); // Set user_id
            stmt.setInt(2, rental.getVehicle().getVehicleId()); // Set vehicle_id
            stmt.setDate(3, new java.sql.Date(rental.getStartDate().getTime())); // Set start_date
            stmt.setDate(4, new java.sql.Date(rental.getEndDate().getTime())); // Set end_date
            stmt.setDouble(5, rental.getTotalCost()); // Set total_cost

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rental.setRentalId(generatedKeys.getInt(1)); // Set rentalId
                        return rental.getRentalId(); // Return rentalId
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error inserting rental", e);
        }
        return 0; // If rental insertion fails
    }

    // Update an existing rental
    public static  boolean updateRental(Rental rental) throws SQLException {
        String query = "UPDATE rentals SET user_id = ?, vehicle_id = ?, start_date = ?, end_date = ?, total_cost = ? WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rental.getUser().getUserID());
            stmt.setInt(2, rental.getVehicle().getVehicleId());
            stmt.setDate(3, new java.sql.Date(rental.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(rental.getEndDate().getTime()));
            stmt.setDouble(5, rental.getTotalCost());
            stmt.setInt(6, rental.getRentalId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete a rental by its ID
    public static boolean deleteRental(int rentalId) throws SQLException {
        String query = "DELETE FROM rentals WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve all rentals
    public  static List<Rental> getAllRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String query = "SELECT r.rental_id, r.user_id, u.user_name, r.vehicle_id, v.type_name, v.license_plate, v.price_per_day, r.start_date, r.end_date, r.total_cost " +
                "FROM rentals r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN vehicles v ON r.vehicle_id = v.vehicle_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("user_name"));
                Vehicle vehicle = new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("type_name"),
                        rs.getString("license_plate"),
                        rs.getDouble("price_per_day")
                );
                rentals.add(new Rental(
                        rs.getInt("rental_id"),
                        user,
                        vehicle,
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getDouble("total_cost")
                ));
            }
        }
        return rentals;
    }
}
