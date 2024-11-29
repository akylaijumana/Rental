package com.example.vehicle.model;

public class Vehicle {
    private int vehicleId;
    private String type;
    private String licensePlate;
    private double pricePerDay;

    public Vehicle(int vehicleId, String type, String licensePlate, double pricePerDay) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
