package com.example.firebaselogin.Models;
import java.io.Serializable;

public class Drink implements Serializable {

    // Properties

    private String id;
    private String name;
    private double price;
    private String fk_brand;

    // Constructors

    public Drink() {

    }

    public Drink(String id, String name, double price, String fk_brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fk_brand = fk_brand;
    }

    // Getters and Setters


    public String getFk_brand() {
        return fk_brand;
    }

    public void setFk_brand(String fk_brand) {
        this.fk_brand = fk_brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
