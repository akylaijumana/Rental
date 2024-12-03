package com.example.vehicle.dao;

import com.example.vehicle.database.DBConnection;
import com.example.vehicle.model.Vehicle;
import com.example.vehicle.model.TypeVehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    private TypeDAO typeVehicleDAO = new TypeDAO();

    public Vehicle getVehicleById(int vehicleId) throws SQLException {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TypeVehicle type = typeVehicleDAO.getTypeByName(rs.getString("type_name"));
                    return new Vehicle(
                            rs.getInt("vehicle_id"),
                            type.getTypeName(),
                            rs.getString("license_plate"),
                            rs.getDouble("price_per_day")
                    );
                }
            }
        }
        return null;
    }

    public boolean addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles (type_name, license_plate, price_per_day) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vehicle.getTypeName());
            stmt.setString(2, vehicle.getLicensePlate());
            stmt.setDouble(3, vehicle.getPricePerDay());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vehicle.setVehicleId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicles SET type_name = ?, license_plate = ?, price_per_day = ? WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicle.getTypeName());
            stmt.setString(2, vehicle.getLicensePlate());
            stmt.setDouble(3, vehicle.getPricePerDay());
            stmt.setInt(4, vehicle.getVehicleId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteVehicle(int vehicleId) throws SQLException {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            return stmt.executeUpdate() > 0;
        }
    }
    public List<String> getAllVehicles() throws SQLException {
        String query = "SELECT v.vehicle_id, v.type_name, v.license_plate, v.price_per_day "
                + "FROM vehicles v";
        List<String> vehicleDetails = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String vehicleDetail = String.format("Vehicle ID: %d, Type: %s, License Plate: %s, Price Per Day: %.2f",
                        rs.getInt("vehicle_id"),
                        rs.getString("type_name"),
                        rs.getString("license_plate"),
                        rs.getDouble("price_per_day"));
                vehicleDetails.add(vehicleDetail);
            }
        }
        return vehicleDetails;
    }

}
