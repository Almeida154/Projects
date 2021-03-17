package com.example.firebaselogin.Models;

public class User {

    // Properties

    private String id;
    private String username;
    private String password;
    private String email;
    private String number;

    // Constructors

    public User() {

    }

    public User(String id, String username, String password, String email, String number) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.number = number;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
