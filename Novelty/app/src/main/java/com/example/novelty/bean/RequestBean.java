package com.example.novelty.bean;

public class RequestBean {
    private String book;
    private String status;
    private UserBean from;
    private String ower;
    private String location;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFrom(UserBean from) {
        this.from = from;
    }

    public void setOwer(String ower) {
        this.ower = ower;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBook() {
        return book;
    }

    public String getStatus() {
        return status;
    }

    public UserBean getFrom() {
        return from;
    }

    public String getOwer() {
        return ower;
    }

    public String getLocation() {
        return location;
    }

    public RequestBean(String book, String status, String ower, String description) {
        this.book = book;
        this.status = status;
        this.ower = ower;
        this.description = description;
    }
}
