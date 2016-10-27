package com.timemanager.model;

        import java.util.Date;

public class Activity {
    private int id;
    private int category;
    private int subcategory;
    private Date startTime;
    private Date finishTime;
    private String description;
    private User user;

    public Activity(int category, int subcategory, Date startTime, Date finishTime, User user, String description) {
        this.category = category;
        this.subcategory = subcategory;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.description = description;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(int subcategory) {
        this.subcategory = subcategory;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
