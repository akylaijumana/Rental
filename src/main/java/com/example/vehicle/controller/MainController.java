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
import java.util.Date;

public class MainController {

    // Vehicle fields
    @FXML private TextField vehicleTypeField;
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

    // ListViews
    @FXML private ListView<String> vehicleListView;
    @FXML private ListView<String> userListView;
    @FXML private ListView<String> rentalListView;

    // DAOs
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private UserDAO userDAO = new UserDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    // Observable lists for ListViews
    private ObservableList<String> vehicleListItems = FXCollections.observableArrayList();
    private ObservableList<String> userListItems = FXCollections.observableArrayList();
    private ObservableList<String> rentalListItems = FXCollections.observableArrayList();

    // Handle Add Vehicle
    @FXML
    public void handleAddVehicle() {
        String typeName = vehicleTypeField.getText();
        String licensePlate = vehicleLicensePlateField.getText();
        double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

        Vehicle vehicle = new Vehicle(0, 0, typeName, licensePlate, pricePerDay);
        try {
            if (vehicleDAO.addVehicle(vehicle)) {
                updateListView();
                clearVehicleFields();
            } else {
                System.out.println("Failed to add vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
    }
    @FXML
    public void handleUpdateVehicle() {
        try {
            // Parse the input fields
            int vehicleId = Integer.parseInt(vehicleLicensePlateField.getText());
            String typeName = vehicleTypeField.getText();
            String licensePlate = vehicleLicensePlateField.getText();
            double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

            int typeId = vehicleDAO.getTypeIdByTypeName(typeName);

            // Create a Vehicle object
            Vehicle vehicle = new Vehicle(vehicleId, typeId, typeName, licensePlate, pricePerDay);

            // Update the vehicle using DAO
            if (vehicleDAO.updateVehicle(vehicle)) {
                updateListView(); // Refresh the UI
            } else {
                System.out.println("Failed to update vehicle.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error updating vehicle: " + e.getMessage());
        }
    }


    // Handle Delete Vehicle
    @FXML
    public void handleDeleteVehicle() {
        int vehicleId = Integer.parseInt(vehicleLicensePlateField.getText());
        try {
            if (vehicleDAO.deleteVehicle(vehicleId)) {
                updateListView();
            } else {
                System.out.println("Failed to delete vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }

    // Handle Add User
    @FXML
    public void handleAddUser() {
        String userName = userNameField.getText();
        int userId = Integer.parseInt(userIdField.getText());

        User user = new User(userId, userName);
        try {
            if (userDAO.insertUser(user)) {
                updateListView();
                clearUserFields();
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // Handle Update User
    @FXML
    public void handleUpdateUser() {
        int userId = Integer.parseInt(userIdField.getText());
        String userName = userNameField.getText();

        User user = new User(userId, userName);
        try {
            if (userDAO.updateUser(user)) {
                updateListView();
            } else {
                System.out.println("Failed to update user.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    // Handle Delete User
    @FXML
    public void handleDeleteUser() {
        int userId = Integer.parseInt(userIdField.getText());
        try {
            if (userDAO.deleteUser(userId)) {
                updateListView();
            } else {
                System.out.println("Failed to delete user.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    // Handle Add Rental
    @FXML
    public void handleAddRental() {
        try {
            int userId = Integer.parseInt(rentalUserIdField.getText());
            int vehicleId = Integer.parseInt(rentalVehicleIdField.getText());
            String startDateStr = startDateField.getValue().toString();
            String endDateStr = endDateField.getValue().toString();

            User user = userDAO.getUserById(userId);
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

            if (user == null) {
                System.out.println("Error: User with ID " + userId + " not found.");
                return;
            }
            if (vehicle == null) {
                System.out.println("Error: Vehicle with ID " + vehicleId + " not found.");
                return;
            }

            java.util.Date startDate = java.sql.Date.valueOf(startDateStr);
            java.util.Date endDate = java.sql.Date.valueOf(endDateStr);

            long durationInMillis = endDate.getTime() - startDate.getTime();
            if (durationInMillis <= 0) {
                System.out.println("Error: End date must be after start date.");
                return;
            }

            long days = durationInMillis / (1000 * 60 * 60 * 24);
            double totalCost = vehicle.getPricePerDay() * days;

            Rental rental = new Rental(0, user, vehicle, startDate, endDate, totalCost);

            if (rentalDAO.addRental(rental)) {
                System.out.println("Rental added successfully: " + rental);
                updateListView();
                clearRentalFields();
            } else {
                System.out.println("Failed to add rental. Please check DAO implementation.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input format. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error adding rental: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Handle Update Rental
    @FXML
    public void handleUpdateRental() {
        int rentalId = Integer.parseInt(rentalIdField.getText());
        int userId = Integer.parseInt(rentalUserIdField.getText());
        int vehicleId = Integer.parseInt(rentalVehicleIdField.getText());
        String startDateStr = startDateField.getValue().toString();
        String endDateStr = endDateField.getValue().toString();

        try {
            User user = userDAO.getUserById(userId);
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

            java.util.Date startDate = java.sql.Date.valueOf(startDateStr);
            java.util.Date endDate = java.sql.Date.valueOf(endDateStr);

            double totalCost = vehicle.getPricePerDay() * (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24); // simple day calculation

            Rental rental = new Rental(rentalId, user, vehicle, startDate, endDate, totalCost);

            if (rentalDAO.updateRental(rental)) {
                updateListView();
            } else {
                System.out.println("Failed to update rental.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating rental: " + e.getMessage());
        }
    }

    // Handle Delete Rental
    @FXML
    public void handleDeleteRental() {
        int rentalId = Integer.parseInt(rentalIdField.getText());
        try {
            if (rentalDAO.deleteRental(rentalId)) {
                updateListView();
            } else {
                System.out.println("Failed to delete rental.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting rental: " + e.getMessage());
        }
    }

    // Update the ListViews with data
    private void updateListView() {
        vehicleListItems.clear();
        userListItems.clear();
        rentalListItems.clear();

        try {
            // Update vehicle list
            vehicleListItems.addAll(vehicleDAO.getAllVehicles());
            vehicleListView.setItems(vehicleListItems);

            // Update user list
            userListItems.addAll(userDAO.getAllUsers());
            userListView.setItems(userListItems);

            // Update rental list
            rentalListItems.addAll(rentalDAO.getAllRentals());
            rentalListView.setItems(rentalListItems);

        } catch (SQLException e) {
            System.out.println("Error updating list view: " + e.getMessage());
        }
    }

    private void clearVehicleFields() {
        vehicleTypeField.clear();
        vehicleLicensePlateField.clear();
        vehiclePriceField.clear();
    }

    private void clearUserFields() {
        userNameField.clear();
        userIdField.clear();
    }

    private void clearRentalFields() {
        rentalUserIdField.clear();
        rentalVehicleIdField.clear();
        startDateField.setValue(null);
        endDateField.setValue(null);
    }
}
