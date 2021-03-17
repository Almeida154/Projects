package com.example.firebaselogin.Models;
import java.util.List;

public class Brand {

    // Properties

    private String id;
    private String desc;
    private String url;

    private List<Drink> drinkArrayList;

    // Constructors

    public Brand() {

    }

    public Brand(String desc) {
        this.desc = desc;
    }

    public Brand(String id, String desc, String url, List<Drink> drinkArrayList) {
        this.id = id;
        this.desc = desc;
        this.url = url;
        this.drinkArrayList = drinkArrayList;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Drink> getDrinkArrayList() {
        return drinkArrayList;
    }

    public void setDrinkArrayList(List<Drink> drinkArrayList) {
        this.drinkArrayList = drinkArrayList;
    }

    // toString

    @Override
    public String toString() {
        return this.desc;
    }
}
