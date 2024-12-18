package com.example.vehicle;
import com.example.vehicle.model.Rental;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {
    private Rental rental;
    private User user;
    private Vehicle vehicle;
    @BeforeEach
    void setUp() {
        user = new User(1, "Test User");
        vehicle = new Vehicle(1, "Car", "ABC123", 50.0);
        rental = new Rental(1, user, vehicle, new Date(2024 - 1900, 0, 1), new Date(2024 - 1900, 0, 10), 500.0);
    }
    @Test
    void testGetters() {
        assertEquals(1, rental.getRentalId(), "Rental ID should be 1");
        assertEquals(user, rental.getUser(), "User should match the test user");
        assertEquals(vehicle, rental.getVehicle(), "Vehicle should match the test vehicle");
        assertEquals(new Date(2024 - 1900, 0, 1), rental.getStartDate(), "Start date should match");
        assertEquals(new Date(2024 - 1900, 0, 10), rental.getEndDate(), "End date should match");
        assertEquals(500.0, rental.getTotalCost(), "Total cost should be 500.0");
    }
    @Test
    void testSetters() {
        User newUser = new User(2, "New User");
        Vehicle newVehicle = new Vehicle(2, "Truck", "XYZ789", 100.0);
        Date newStartDate = new Date(2024 - 1900, 1, 1);
        Date newEndDate = new Date(2024 - 1900, 1, 15);
        double newTotalCost = 1500.0;
        rental.setRentalId(2);
        rental.setUser(newUser);
        rental.setVehicle(newVehicle);
        rental.setStartDate(newStartDate);
        rental.setEndDate(newEndDate);
        rental.setTotalCost(newTotalCost);

        assertEquals(2, rental.getRentalId(), "Rental ID should be updated to 2");
        assertEquals(newUser, rental.getUser(), "User should be updated to new user");
        assertEquals(newVehicle, rental.getVehicle(), "Vehicle should be updated to new vehicle");
        assertEquals(newStartDate, rental.getStartDate(), "Start date should be updated");
        assertEquals(newEndDate, rental.getEndDate(), "End date should be updated");
        assertEquals(1500.0, rental.getTotalCost(), "Total cost should be updated to 1500.0");
    }
    @Test
    void testToString() {
        String expected = "Rental{" +
                "rentalId=1" +
                ", user=" + user +
                ", vehicle=" + vehicle +
                ", startDate=" + new Date(2024 - 1900, 0, 1) +
                ", endDate=" + new Date(2024 - 1900, 0, 10) +
                ", totalCost=500.0" +
                '}';
        assertEquals(expected, rental.toString(), "toString method should return the correct string representation");
    }
}
