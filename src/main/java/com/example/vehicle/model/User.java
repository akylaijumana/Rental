package com.example.vehicle.model;

public class User {
    private String name;
    private int userID;

    public User(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }
    public User(String name) {
        this.name = name;
        this.userID = 0;
    }
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

    public String getId() {
        return null;
    }
}
