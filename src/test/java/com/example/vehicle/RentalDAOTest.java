package com.example.vehicle;

import com.example.vehicle.dao.RentalDAO;
import com.example.vehicle.model.Rental;
import com.example.vehicle.model.User;
import com.example.vehicle.model.Vehicle;
import org.junit.jupiter.api.*;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentalDAOTest {

    private RentalDAO rentalDAO;

    @BeforeEach
    void setUp() {
        rentalDAO = new RentalDAO();
    }

    @Test
    void testAddRental() throws Exception {
        User testUser = new User(1, "Test User");
        Vehicle testVehicle = new Vehicle(1, "Car", "ABC123", 50.0);
        Rental rental = new Rental(0, testUser, testVehicle,
                Date.valueOf("2024-01-01"), Date.valueOf("2024-01-10"), 500.0);

        int rentalId = rentalDAO.addRental(rental);

        assertTrue(rentalId > 0, "Rental ID should be greater than 0");
        Rental fetchedRental = rentalDAO.getRentalById(rentalId);
        assertNotNull(fetchedRental, "Fetched rental should not be null");
        assertEquals("Test User", fetchedRental.getUser().getName());
        assertEquals("Car", fetchedRental.getVehicle().getTypeName());
    }

    @Test
    void testGetRentalById() throws Exception {
        int rentalId = 1; // Replace with a valid rental ID in your test database
        Rental rental = rentalDAO.getRentalById(rentalId);

        assertNotNull(rental, "Rental should not be null");
        assertEquals(rentalId, rental.getRentalId());
    }

    @Test
    void testUpdateRental() throws Exception {
        int rentalId = 1; // Replace with a valid rental ID in your test database
        Rental rental = rentalDAO.getRentalById(rentalId);
        assertNotNull(rental, "Rental should exist for updating");

        rental.setTotalCost(750.0);
        boolean updated = rentalDAO.updateRental(rental);

        assertTrue(updated, "Rental update should return true");
        Rental updatedRental = rentalDAO.getRentalById(rentalId);
        assertEquals(750.0, updatedRental.getTotalCost(), "Total cost should be updated");
    }

    @Test
    void testDeleteRental() throws Exception {
        User testUser = new User(1, "Test User");
        Vehicle testVehicle = new Vehicle(1, "Car", "ABC123", 50.0);
        Rental rental = new Rental(0, testUser, testVehicle,
                Date.valueOf("2024-01-01"), Date.valueOf("2024-01-10"), 500.0);

        int rentalId = rentalDAO.addRental(rental);
        assertTrue(rentalId > 0, "Rental should be added successfully");

        boolean deleted = rentalDAO.deleteRental(rentalId);
        assertTrue(deleted, "Rental should be deleted successfully");

        Rental fetchedRental = rentalDAO.getRentalById(rentalId);
        assertNull(fetchedRental, "Fetched rental should be null after deletion");
    }

    @Test
    void testGetAllRentals() throws Exception {
        List<Rental> rentals = rentalDAO.getAllRentals();
        assertNotNull(rentals, "Rental list should not be null");
        assertTrue(rentals.size() > 0, "Rental list should have at least one rental");
    }
}

