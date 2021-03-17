package com.example.jsonrequest.Models;
import java.io.Serializable;

public class Drink implements Serializable {

    // Properties

    private int id;
    private String product;
    private double price;

    // Constructor

    public Drink(int id, String product, double price) {
        this.id = id;
        this.product = product;
        this.price = price;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
