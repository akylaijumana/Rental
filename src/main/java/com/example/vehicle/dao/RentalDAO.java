package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Rental;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    public Rental getRentalById(int rentalId) throws SQLException {
        String query = "SELECT r.rental_id, r.start_date, r.end_date, r.total_cost, u.user_name AS user_name, " +
                "v.vehicle_id, v.type_id, v.type_name, v.license_plate, v.price_per_day " +
                "FROM rentals r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN vehicles v ON r.vehicle_id = v.vehicle_id " +
                "WHERE r.rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("user_name");

                return new Rental(
                        rs.getInt("rental_id"),
                        new User(rs.getInt("user_id"), userName),
                        new Vehicle(
                                rs.getInt("vehicle_id"),
                                rs.getInt("type_id"),
                                rs.getString("type_name"),
                                rs.getString("license_plate"),
                                rs.getDouble("price_per_day")
                        ),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getDouble("total_cost")
                );
            }
        }
        return null;
    }

    public boolean addRental(Rental rental) throws SQLException {
        String query = "INSERT INTO rentals (user_id, vehicle_id, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rental.getUser().getUserID());
            stmt.setInt(2, rental.getVehicle().getVehicleId());
            stmt.setDate(3, new java.sql.Date(rental.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(rental.getEndDate().getTime()));
            stmt.setDouble(5, rental.getTotalCost());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateRental(Rental rental) throws SQLException {
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

    public boolean deleteRental(int rentalId) throws SQLException {
        String query = "DELETE FROM rentals WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rentalId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<String> getAllRentals() throws SQLException {
        String query = "SELECT r.rental_id, r.start_date, r.end_date, r.total_cost, u.user_name AS user_name, " +
                "v.type_name AS vehicle_type " +
                "FROM rentals r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN vehicles v ON r.vehicle_id = v.vehicle_id";
        List<String> rentalsDetails = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String rentalDetail = String.format("Rental ID: %d, User: %s, Vehicle: %s, Total Cost: %.2f",
                        rs.getInt("rental_id"),
                        rs.getString("user_name"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("total_cost"));
                rentalsDetails.add(rentalDetail);
            }
        }
        return rentalsDetails;
    }
}
