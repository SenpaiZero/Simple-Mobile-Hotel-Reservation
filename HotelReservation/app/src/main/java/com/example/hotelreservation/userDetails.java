package com.example.hotelreservation;

public class userDetails {
    private static userDetails instance;

    private String password;
    private String username;
    private String email;
    private String phoneNumber;
    private String fullName;

    private userDetails() {
    }

    public static synchronized userDetails getInstance() {
        if (instance == null) {
            instance = new userDetails();
        }
        return instance;
    }

    // Getters and setters for user details
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
