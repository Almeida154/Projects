package com.example.firebaselogin.Models;

public class ItemOrdered {

    // Properties

    private String id;
    private Drink drink;
    private int quantity = 1;
    private double subTotal;

    // Constructors

    public ItemOrdered() {

    }

    public ItemOrdered(String id, int quantity, Drink drink) {
        this.id = id;
        this.drink = drink;
        this.quantity = quantity;
        this.subTotal = quantity * drink.getPrice();
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subTotal = quantity * drink.getPrice();
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }
}
