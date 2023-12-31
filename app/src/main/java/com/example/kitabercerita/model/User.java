package com.example.kitabercerita.model;

public class User {
    private static User currentUser;
    private String username;
    private String email;
    private String password;
    private String status;
    private String phoneNumber;
    private String image;

    public User(String username, String email, String password, String status, String phoneNumber, String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
