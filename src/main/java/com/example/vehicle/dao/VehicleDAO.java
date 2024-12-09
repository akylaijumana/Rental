package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public static Vehicle getVehicleById(int vehicleId) throws SQLException {
        String query = "SELECT v.vehicle_id, v.price_per_day, v.type_name, v.license_plate "
                + "FROM vehicles v "
                + "WHERE v.vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);  // Setting the vehicle ID parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getInt("vehicle_id"),
                            rs.getString("type_name"),
                            rs.getString("license_plate"),
                            rs.getDouble("price_per_day")
                    );
                }
            }
        }
        return null;
    }

    // Add a new vehicle to the database
    public static int addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles (vehicle_id, type_name, license_plate, price_per_day) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the user-provided vehicle_id and other fields
            stmt.setInt(1, vehicle.getVehicleId());
            stmt.setString(2, vehicle.getTypeName());
            stmt.setString(3, vehicle.getLicensePlate());
            stmt.setDouble(4, vehicle.getPricePerDay());

            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected > 0) ? vehicle.getVehicleId() : 0; // Return the vehicle ID if successful
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            throw e;
        }
    }



    // Update an existing vehicle
    public static boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicles SET type_name = ?, license_plate = ?, price_per_day = ? WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getTypeName());
            stmt.setString(2, vehicle.getLicensePlate());
            stmt.setDouble(3, vehicle.getPricePerDay());
            stmt.setInt(4, vehicle.getVehicleId());
            return stmt.executeUpdate() > 0; // Return true if at least one row is updated
        }
    }

    // Delete a vehicle by its ID
    public static boolean deleteVehicle(int vehicleId) throws SQLException {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            return stmt.executeUpdate() > 0; // Return true if at least one row is deleted
        }
    }

    // Retrieve all vehicles
    public static List<Vehicle> getAllVehicles() throws SQLException {
        String query = "SELECT v.vehicle_id, v.type_name, v.license_plate, v.price_per_day "
                + "FROM vehicles v ";

        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("type_name"),
                        rs.getString("license_plate"),
                        rs.getDouble("price_per_day")
                );
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

}