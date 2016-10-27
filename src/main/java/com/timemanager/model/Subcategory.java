package com.timemanager.model;

public class Subcategory {

    private int id;
    private String name;
    private User user;

    public Subcategory(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Subcategory(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
