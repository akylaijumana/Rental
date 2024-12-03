package com.example.vehicle.model;

public class Vehicle {
    private int vehicleId;
    private String typeName;
    private String licensePlate;
    private double pricePerDay;

    public Vehicle(int vehicleId, String typeName, String licensePlate, double pricePerDay) {
        this.vehicleId = vehicleId;
        this.typeName = typeName;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
