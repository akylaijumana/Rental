package com.example.vehicle.model;

public class TypeVehicle extends Vehicle {
    private String typeName;  // Can be "Bus", "Car", or "Motorcycle"

    public TypeVehicle(int vehicleId, String typeName, String licensePlate, double pricePerDay) {
        super(vehicleId, typeName, licensePlate, pricePerDay);  // Passing to the base class Vehicle
        this.typeName = typeName;  // Setting the type of the vehicle
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
