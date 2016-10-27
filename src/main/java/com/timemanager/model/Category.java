package com.timemanager.model;

public class Category {

    private int id;
    private String name;
    private User user;

    public Category(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Category(String name, User user) {
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
