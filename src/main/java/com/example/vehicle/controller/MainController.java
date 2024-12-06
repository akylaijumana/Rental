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
    @FXML private TextField vehicleIdField;
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
    @FXML private ListView<Vehicle> vehicleListView;
    @FXML private ListView<User> userListView;
    @FXML private ListView<Rental> rentalListView;

    // DAOs
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private UserDAO userDAO = new UserDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    // Observable lists for ListViews
    private ObservableList<Vehicle> vehicleListItems = FXCollections.observableArrayList();
    private ObservableList<User> userListItems = FXCollections.observableArrayList();
    private ObservableList<Rental> rentalListItems = FXCollections.observableArrayList();

    public void initialize() {
        try {
            // Populate the list of vehicles, users, and rentals with actual objects
            userListItems = FXCollections.observableList(userDAO.getAllUsers());
            userListView.setItems(userListItems);

            vehicleListItems = FXCollections.observableList(vehicleDAO.getAllVehicles());
            vehicleListView.setItems(vehicleListItems);

            rentalListItems = FXCollections.observableList(rentalDAO.getAllRentals());
            rentalListView.setItems(rentalListItems);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    @FXML
    public void handleAddVehicle() {
        String vehicleIdText = vehicleIdField.getText(); // Get the user input for vehicle ID
        String typeName = vehicleTypeField.getText();
        String licensePlate = vehicleLicensePlateField.getText();
        double pricePerDay = Double.parseDouble(vehiclePriceField.getText());

        try {
            // Validate vehicle ID input (ensure it's a valid number and not zero)
            int vehicleId = Integer.parseInt(vehicleIdText);
            if (vehicleId <= 0) {
                System.out.println("Error: Vehicle ID must be a positive integer.");
                return;
            }

            // Create the Vehicle object with the manually input vehicle ID
            Vehicle vehicle = new Vehicle(vehicleId, typeName, licensePlate, pricePerDay);

            // Add the vehicle using the DAO
            int id = vehicleDAO.addVehicle(vehicle);
            if (id > 0) {
                vehicle.setVehicleId(id); // Set the generated ID in the Vehicle object
                vehicleListItems.add(vehicle);
                clearVehicleFields();
            } else {
                System.out.println("Failed to add vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid vehicle ID input.");
        }
    }



    @FXML
    public void handleUpdateVehicle() {
        try {
            // Parse the input fields
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            String typeName = vehicleTypeField.getText();
            String licensePlate = vehicleLicensePlateField.getText();
            double pricePerDay = Double.parseDouble(vehiclePriceField.getText());


            // Create a Vehicle object
            Vehicle vehicle = new Vehicle(vehicleId, typeName, licensePlate, pricePerDay);

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

    @FXML
    public void handleDeleteVehicle() {
        try {
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            if (vehicleDAO.deleteVehicle(vehicleId)) {
                updateListView();
            } else {
                System.out.println("Failed to delete vehicle.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }

    @FXML
    public void handleAddUser() {
        String userName = userNameField.getText();

        User user = new User(0, userName);
        try {
            int id = userDAO.insertUser(user);
            user.setUserID(id);

            if (id > 0) {
                userListItems.add(user);
                clearUserFields();
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

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
            int rentalId = rentalDAO.addRental(rental);
            if (rentalId > 0) {
                System.out.println("Rental added successfully: " + rental);
                updateListView();
                clearRentalFields();
            } else {
                System.out.println("Failed to add rental.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input format. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error adding rental: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdateRental() {
        int rentalId = Integer.parseInt(rentalIdField.getText());
        int userId = Integer.parseInt(rentalUserIdField.getText());
        int vehicleId = Integer.parseInt(rentalVehicleIdField.getText());
        String startDateStr = startDateField.getValue().toString();
        String endDateStr = endDateField.getValue().toString();

        try {
            Rental rental = rentalDAO.getRentalById(rentalId);
            User user = userDAO.getUserById(userId);
            Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

            if (user == null || vehicle == null) {
                System.out.println("Error: User or vehicle not found.");
                return;
            }

            rental.setUser(user);
            rental.setVehicle(vehicle);
            rental.setStartDate(java.sql.Date.valueOf(startDateStr));
            rental.setEndDate(java.sql.Date.valueOf(endDateStr));

            if (rentalDAO.updateRental(rental)) {
                updateListView();
            } else {
                System.out.println("Failed to update rental.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating rental: " + e.getMessage());
        }
    }

    @FXML
    public void handleDeleteRental() {
        try {
            int rentalId = Integer.parseInt(rentalIdField.getText());
            if (rentalDAO.deleteRental(rentalId)) {
                updateListView();
            } else {
                System.out.println("Failed to delete rental.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting rental: " + e.getMessage());
        }
    }

    private void updateListView() {
        try {
            userListItems.setAll(userDAO.getAllUsers());
            vehicleListItems.setAll(vehicleDAO.getAllVehicles());
            rentalListItems.setAll(rentalDAO.getAllRentals());
        } catch (SQLException e) {
            System.out.println("Error updating list view: " + e.getMessage());
        }
    }

    private void clearVehicleFields() {
        vehicleIdField.clear();
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
