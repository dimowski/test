package com.timemanager.model;

import java.util.Date;

public class Activity {
    private int id;
    private String category;
    private String subcategory;
    private Date startTime;
    private Date finishTime;
    private String description;

    public Activity(String category, String subcategory, Date startTime, Date finishTime, String description) {
        this.category = category;
        this.subcategory = subcategory;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
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
}
