package com.example.vehicle;

import com.example.vehicle.dao.VehicleDAO;
import com.example.vehicle.dao.UserDAO;
import com.example.vehicle.dao.RentalDAO;
import com.example.vehicle.model.Vehicle;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Rental;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import java.sql.*;
import java.util.Date;

public class MainController {

    // Vehicle fields
    @FXML private TextField vehicleModelField;
    @FXML private TextField vehicleLicensePlateField;
    @FXML private TextField vehiclePriceField;
    @FXML private Button addVehicleButton;
    @FXML private Button updateVehicleButton;
    @FXML private Button deleteVehicleButton;

    // User fields
    @FXML private TextField userNameField;
    @FXML private TextField userIdField;  // Added a field for the user ID
    @FXML private Button addUserButton;
    @FXML private Button updateUserButton;
    @FXML private Button deleteUserButton;

    // Rental fields
    @FXML private TextField rentalUserIdField;
    @FXML private TextField rentalVehicleIdField;
    @FXML private DatePicker startDateField;  // Assuming you are using a DatePicker for dates
    @FXML private DatePicker endDateField;    // Assuming you are using a DatePicker for dates
    @FXML private Button addRentalButton;

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private UserDAO userDAO = new UserDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    // Vehicle CRUD operations
    @FXML
    public void handleAddVehicle() {
        String model = vehicleModelField.getText();
        String licensePlate = vehicleLicensePlateField.getText();
        double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

        Vehicle vehicle = new Vehicle(0, model, licensePlate, pricePerDay);
        try {
            boolean success = vehicleDAO.addVehicle(vehicle);
            if (success) {
                System.out.println("Vehicle added successfully.");
            } else {
                System.out.println("Failed to add vehicle.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateVehicle() {
        int vehicleId = Integer.parseInt(vehicleModelField.getText());
        String model = vehicleModelField.getText();
        String licensePlate = vehicleLicensePlateField.getText();
        double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

        Vehicle vehicle = new Vehicle(vehicleId, model, licensePlate, pricePerDay);
        try {
            boolean success = vehicleDAO.updateVehicle(vehicle);
            if (success) {
                System.out.println("Vehicle updated successfully.");
            } else {
                System.out.println("Failed to update vehicle.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteVehicle() {
        int vehicleId = Integer.parseInt(vehicleModelField.getText());
        try {
            boolean success = vehicleDAO.deleteVehicle(vehicleId);
            if (success) {
                System.out.println("Vehicle deleted successfully.");
            } else {
                System.out.println("Failed to delete vehicle.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // User CRUD operations
    @FXML
    public void handleAddUser() {
        String name = userNameField.getText();

        User user = new User(name);
        try {
            boolean success = userDAO.insertUser(user);
            if (success) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateUser() {
        int userId = Integer.parseInt(userIdField.getText());
        String name = userNameField.getText();

        User user = new User(userId, name);
        try {
            boolean success = userDAO.updateUser(user);
            if (success) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("Failed to update user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteUser() {
        int userId = Integer.parseInt(userIdField.getText());
        try {
            boolean success = userDAO.deleteUser(userId);
            if (success) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rental CRUD operation
    @FXML
    public void handleAddRental() {
        int userId = Integer.parseInt(rentalUserIdField.getText());
        int vehicleId = Integer.parseInt(rentalVehicleIdField.getText());

        try {
            // Fetch User and Vehicle from the database
            User user = userDAO.getUserById(userId);  // Assuming you have a method to get user by ID
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);  // Assuming you have a method to get vehicle by ID

            // Ensure the user and vehicle are found
            if (user != null && vehicle != null) {
                // Get start date and end date from DatePickers (convert them to java.util.Date)
                Date startDate = java.sql.Date.valueOf(startDateField.getValue());  // Convert LocalDate to java.util.Date
                Date endDate = java.sql.Date.valueOf(endDateField.getValue());  // Convert LocalDate to java.util.Date

                // Calculate total cost (example: days * price per day)
                long durationInMilliSeconds = endDate.getTime() - startDate.getTime();
                long durationInDays = durationInMilliSeconds / (1000 * 60 * 60 * 24);
                double totalCost = durationInDays * vehicle.getPricePerDay();

                // Create Rental object
                Rental rental = new Rental(0, user, vehicle, startDate, endDate, totalCost);

                // Add rental to database
                boolean success = rentalDAO.addRental(rental);
                if (success) {
                    System.out.println("Rental added successfully.");
                } else {
                    System.out.println("Failed to add rental.");
                }
            } else {
                System.out.println("User or Vehicle not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
