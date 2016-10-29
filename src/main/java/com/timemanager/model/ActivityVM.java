package com.timemanager.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityVM {
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
    private String category;
    private String subcategory;
    private Date startTime;
    private Date finishTime;
    private String description;

    private String start;
    private String finish;
    private String duration;
    private String date;

    public ActivityVM(String category, String subcategory, Date startTime, Date finishTime, String description) {
        this.category = category;
        this.subcategory = subcategory;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.description = description;
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

    public String getStart() {
        return sdf.format(startTime);
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return sdf.format(finishTime);
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getDate() {
        return sdf2.format(startTime);
    }

    public String getDuration() {
        long diff = finishTime.getTime() - startTime.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffMinutes = diff / (60 * 1000) % 60;
        return "Total: " + diffHours + " hour(s) " + diffMinutes + " minute(s)";
    }
}
