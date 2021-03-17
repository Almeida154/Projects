package com.example.firebaselogin.Models;

public class Coupon {

    // Properties

    private String id;
    private String name;
    private double discount;
    private double minPrice;

    // Constructors

    public Coupon() {

    }

    public Coupon(String id, String name, double discount, double minPrice) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.minPrice = minPrice;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
}
