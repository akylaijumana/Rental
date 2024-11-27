package com.example.vehicle.model;

public class Vehicle {
    private int vehicleId;
    private String model;
    private String licensePlate;
    private double pricePerDay;

    public Vehicle(int vehicleId, String model, String licensePlate, double pricePerDay) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.licensePlate = licensePlate;
        this.pricePerDay = pricePerDay;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
