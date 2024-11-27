package com.example.vehicle.model;

public class User {
    private String name;
    private int userID;

    // Constructor with both userID and name
    public User(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    // Constructor with just name (for adding a new user without userID)
    public User(String name) {
        this.name = name;
        this.userID = 0;  // Default userID, you can adjust based on your DB logic
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
