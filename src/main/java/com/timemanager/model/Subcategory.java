package com.timemanager.model;

public class Subcategory {

    private int id;
    private String subcategoryName;
    private User user;

    public Subcategory(String subcategoryName, User user) {
        this.subcategoryName = subcategoryName;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
