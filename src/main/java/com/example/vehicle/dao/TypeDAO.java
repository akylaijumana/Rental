package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.TypeVehicle;

import java.sql.*;
import java.util.ArrayList;

public class TypeDAO {

    // Get a TypeVehicle by its typeName
    public TypeVehicle getTypeByName(String typeName) throws SQLException {
        String query = "SELECT * FROM type_vehicle WHERE type_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, typeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TypeVehicle(
                        rs.getInt("type_id"),
                        0, // Placeholder for vehicleId, not applicable in this context
                        rs.getString("type_name"),
                        null, // Placeholder for licensePlate
                        0 // Placeholder for pricePerDay
                );
            }
        }
        return null;
    }

    // Add a new TypeVehicle
    public boolean addTypeVehicle(String typeName) throws SQLException {
        String query = "INSERT INTO type_vehicle (type_name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, typeName);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Update the name of a TypeVehicle
    public boolean updateTypeVehicle(int typeId, String newTypeName) throws SQLException {
        String query = "UPDATE type_vehicle SET type_name = ? WHERE type_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newTypeName);
            stmt.setInt(2, typeId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete a TypeVehicle by its ID
    public boolean deleteTypeVehicle(int typeId) throws SQLException {
        String query = "DELETE FROM type_vehicle WHERE type_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, typeId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Get all vehicle types from the database
    public ArrayList<TypeVehicle> getAllTypeVehicles() throws SQLException {
        String query = "SELECT * FROM type_vehicle";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            ArrayList<TypeVehicle> typeVehicles = new ArrayList<>();
            while (rs.next()) {
                typeVehicles.add(new TypeVehicle(
                        rs.getInt("type_id"),
                        0, // Placeholder for vehicleId, not applicable in this context
                        rs.getString("type_name"),
                        null, // Placeholder for licensePlate
                        0 // Placeholder for pricePerDay
                ));
            }
            return typeVehicles;
        }
    }

    // Get a TypeVehicle by typeId
    public TypeVehicle getTypeById(int typeId) throws SQLException {
        String query = "SELECT * FROM type_vehicle WHERE type_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, typeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TypeVehicle(
                        rs.getInt("type_id"),
                        0, // Placeholder for vehicleId
                        rs.getString("type_name"),
                        null, // Placeholder for licensePlate
                        0 // Placeholder for pricePerDay
                );
            }
        }
        return null;
    }
}
