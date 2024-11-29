package com.example.vehicle.controller;

import com.example.vehicle.dao.VehicleDAO;
import com.example.vehicle.dao.UserDAO;
import com.example.vehicle.dao.RentalDAO;
import com.example.vehicle.model.Vehicle;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Rental;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Arrays;
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
    @FXML private TextField userIdField;
    @FXML private Button addUserButton;
    @FXML private Button updateUserButton;
    @FXML private Button deleteUserButton;

    // Rental fields
    @FXML private TextField rentalUserIdField;
    @FXML private TextField rentalVehicleIdField;
    @FXML private DatePicker startDateField;
    @FXML private DatePicker endDateField;
    @FXML private Button addRentalButton;
    @FXML private TextField rentalIdField;
    @FXML private Button updateRentalButton;
    @FXML private Button deleteRentalButton;
    @FXML private ListView<String> listView;

    private VehicleDAO vehicleDAO = new VehicleDAO();
    private UserDAO userDAO = new UserDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    // Initialize the ObservableList properly
    private ObservableList<String> listViewItems = FXCollections.observableArrayList();

    // Handle adding a vehicle
    @FXML
    public void handleAddVehicle() {
        String model = vehicleModelField.getText();
        String licensePlate = vehicleLicensePlateField.getText();
        double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

        Vehicle vehicle = new Vehicle(0, model, licensePlate, pricePerDay);
        try {
            if (vehicleDAO.addVehicle(vehicle)) {
                System.out.println("Vehicle added successfully.");
                updateListView();
                clearVehicleFields();
            } else {
                System.out.println("Failed to add vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
    }

    // Handle updating a vehicle
    @FXML
    public void handleUpdateVehicle() {
        int vehicleId = Integer.parseInt(vehicleLicensePlateField.getText());
        String model = vehicleModelField.getText();
        String licensePlate = vehicleLicensePlateField.getText();
        double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

        Vehicle vehicle = new Vehicle(vehicleId, model, licensePlate, pricePerDay);
        try {
            if (vehicleDAO.updateVehicle(vehicle)) {
                System.out.println("Vehicle updated successfully.");
                updateListView();
            } else {
                System.out.println("Failed to update vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating vehicle: " + e.getMessage());
        }
    }

    // Handle deleting a vehicle
    @FXML
    public void handleDeleteVehicle() {
        int vehicleId = Integer.parseInt(vehicleLicensePlateField.getText());
        try {
            if (vehicleDAO.deleteVehicle(vehicleId)) {
                System.out.println("Vehicle deleted successfully.");
                updateListView();
            } else {
                System.out.println("Failed to delete vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }

    // Handle adding a user
    @FXML
    public void handleAddUser() {
        String name = userNameField.getText();
        User user = new User(0, name);
        try {
            if (userDAO.insertUser(user)) {
                System.out.println("User added successfully.");
                updateListView();
                clearUserFields();
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // Handle updating a user
    @FXML
    public void handleUpdateUser() {
        int userId = Integer.parseInt(userIdField.getText());
        String name = userNameField.getText();
        User user = new User(userId, name);
        try {
            if (userDAO.updateUser(user)) {
                System.out.println("User updated successfully.");
                updateListView();
            } else {
                System.out.println("Failed to update user.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    // Handle deleting a user
    @FXML
    public void handleDeleteUser() {
        int userId = Integer.parseInt(userIdField.getText());
        try {
            if (userDAO.deleteUser(userId)) {
                System.out.println("User deleted successfully.");
                updateListView();
            } else {
                System.out.println("Failed to delete user.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    // Handle adding a rental
    @FXML
    public void handleAddRental() {
        int userId = Integer.parseInt(rentalUserIdField.getText());
        int vehicleId = Integer.parseInt(rentalVehicleIdField.getText());
        try {
            User user = userDAO.getUserById(userId);
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

            if (user != null && vehicle != null) {
                Date startDate = java.sql.Date.valueOf(startDateField.getValue());
                Date endDate = java.sql.Date.valueOf(endDateField.getValue());

                // Ensure start date is before end date
                if (startDate.after(endDate)) {
                    System.out.println("Start date cannot be after end date.");
                    return;
                }

                long durationInMilliSeconds = endDate.getTime() - startDate.getTime();
                long durationInDays = durationInMilliSeconds / (1000 * 60 * 60 * 24);
                double totalCost = durationInDays * vehicle.getPricePerDay();

                Rental rental = new Rental(0, user, vehicle, startDate, endDate, totalCost);
                if (rentalDAO.addRental(rental)) {
                    System.out.println("Rental added successfully.");
                    updateListView(); // Update ListView after adding rental
                    clearRentalFields();
                } else {
                    System.out.println("Failed to add rental.");
                }
            } else {
                System.out.println("User or Vehicle not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding rental: " + e.getMessage());
        }
    }

    // Handle updating a rental
    @FXML
    public void handleUpdateRental() {
        int rentalId = Integer.parseInt(rentalIdField.getText());
        int userId = Integer.parseInt(rentalUserIdField.getText());
        int vehicleId = Integer.parseInt(rentalVehicleIdField.getText());

        try {
            User user = userDAO.getUserById(userId);
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

            if (user != null && vehicle != null) {
                Date startDate = java.sql.Date.valueOf(startDateField.getValue());
                Date endDate = java.sql.Date.valueOf(endDateField.getValue());

                // Ensure start date is before end date
                if (startDate.after(endDate)) {
                    System.out.println("Start date cannot be after end date.");
                    return;
                }

                long durationInMilliSeconds = endDate.getTime() - startDate.getTime();
                long durationInDays = durationInMilliSeconds / (1000 * 60 * 60 * 24);
                double totalCost = durationInDays * vehicle.getPricePerDay();

                Rental rental = new Rental(rentalId, user, vehicle, startDate, endDate, totalCost);
                if (rentalDAO.updateRental(rental)) {
                    System.out.println("Rental updated successfully.");
                    updateListView();
                } else {
                    System.out.println("Failed to update rental.");
                }
            } else {
                System.out.println("User or Vehicle not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating rental: " + e.getMessage());
        }
    }

    // Handle deleting a rental
    @FXML
    public void handleDeleteRental() {
        int rentalId = Integer.parseInt(rentalIdField.getText());
        try {
            if (rentalDAO.deleteRental(rentalId)) {
                System.out.println("Rental deleted successfully.");
                updateListView();
            } else {
                System.out.println("Failed to delete rental.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting rental: " + e.getMessage());
        }
    }

    // Update ListView with all vehicles, users, and rentals
    private void updateListView() {
        listViewItems.clear();
        try {
            // Get all vehicles and add to ListView
            listViewItems.addAll(Arrays.toString(vehicleDAO.getAllVehicles()));

            // Get all users and add to ListView
            listViewItems.addAll(Arrays.toString(userDAO.getAllUsers()));

            // Get all rentals and add to ListView
            listViewItems.addAll(rentalDAO.getAllRentalsWithDetails());

            listView.setItems(listViewItems);
        } catch (SQLException e) {
            System.out.println("Error updating ListView: " + e.getMessage());
        }
    }

    // Clear vehicle fields
    private void clearVehicleFields() {
        vehicleModelField.clear();
        vehicleLicensePlateField.clear();
        vehiclePriceField.clear();
    }

    // Clear user fields
    private void clearUserFields() {
        userNameField.clear();
        userIdField.clear();
    }

    // Clear rental fields
    private void clearRentalFields() {
        rentalUserIdField.clear();
        rentalVehicleIdField.clear();
        startDateField.setValue(null);
        endDateField.setValue(null);
        rentalIdField.clear();
    }
}
