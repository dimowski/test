package com.timemanager.model;

public class User {

    private int id;
    private String email;
    private String username;
    private String pwd;

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public User(String email, String username, String pwd) {
        this.email = email;
        this.username = username;
        this.pwd = pwd;
    }

    public User(int id, String email, String username, String pwd) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
