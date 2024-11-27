package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Vehicle;
import java.sql.*;

public class VehicleDAO {

    // Get a vehicle by its ID
    public Vehicle getVehicleById(int vehicleId) throws SQLException {
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
        return null;  // If no vehicle found
    }

    // Add a new vehicle
    public boolean addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles (model, license_plate, price_per_day) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getLicensePlate());
            stmt.setDouble(3, vehicle.getPricePerDay());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if insertion is successful
        }
    }

    // Update vehicle details
    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicles SET model = ?, license_plate = ?, price_per_day = ? WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getLicensePlate());
            stmt.setDouble(3, vehicle.getPricePerDay());
            stmt.setInt(4, vehicle.getVehicleId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the update was successful
        }
    }

    // Delete a vehicle by ID
    public boolean deleteVehicle(int vehicleId) throws SQLException {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if deletion was successful
        }
    }
}
