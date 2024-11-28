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

    private ObservableList<String> listViewItems = FXCollections.observableArrayList();

    @FXML
    public void handleAddVehicle() {
    }
    @FXML
    public void handleUpdateVehicle() {
    }
    @FXML
    public void handleDeleteVehicle() {
    }
    @FXML
    public void handleAddUser() {
    }
    @FXML
    public void handleUpdateUser() {
    }
    @FXML
    public void handleDeleteUser() {
    }
    @FXML
    public void handleAddRental() {
    }

    // Update rental method
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

                long durationInMilliSeconds = endDate.getTime() - startDate.getTime();
                long durationInDays = durationInMilliSeconds / (1000 * 60 * 60 * 24);
                double totalCost = durationInDays * vehicle.getPricePerDay();

                Rental rental = new Rental(rentalId, user, vehicle, startDate, endDate, totalCost);
                boolean success = rentalDAO.updateRental(rental);
                if (success) {
                    System.out.println("Rental updated successfully.");
                    updateListView();
                } else {
                    System.out.println("Failed to update rental.");
                }
            } else {
                System.out.println("User or Vehicle not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete rental method
    @FXML
    public void handleDeleteRental() {
        int rentalId = Integer.parseInt(rentalIdField.getText());
        try {
            boolean success = rentalDAO.deleteRental(rentalId);
            if (success) {
                System.out.println("Rental deleted successfully.");
                updateListView();
            } else {
                System.out.println("Failed to delete rental.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the ListView with vehicles, users, and rentals
    private void updateListView() {
        listViewItems.clear();  // Clear current list
        // Fetch all vehicles, users, and rentals from the database
        for (Vehicle vehicle : vehicleDAO.getAllVehicles()) {
            listViewItems.add("Vehicle: " + vehicle.getModel() + " - " + vehicle.getLicensePlate());
        }
        for (User user : userDAO.getAllUsers()) {
            listViewItems.add("User: " + user.getName() + " - ID: " + user.getId());
        }
        for (Rental rental : rentalDAO.getAllRentals()) {
            listViewItems.add("Rental: " + rental.getUser().getName() + " - Vehicle: " + rental.getVehicle().getModel());
        }
        // Update the ListView
        listView.setItems(listViewItems);
    }
}
