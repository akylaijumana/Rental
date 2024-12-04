package com.example.vehicle.model;

public class TypeVehicle extends Vehicle {
    private int typeId;
    private String typeName;

    public TypeVehicle( int typeId ,int vehicleId, String typeName, String licensePlate, double pricePerDay) {
        super(vehicleId,typeId, typeName, licensePlate, pricePerDay);  // Passing to the base class Vehicle
        this.typeName = typeName;
        this.typeId= typeId;
    }

    @Override
    public int getTypeId() {
        return typeId;
    }

    @Override
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
