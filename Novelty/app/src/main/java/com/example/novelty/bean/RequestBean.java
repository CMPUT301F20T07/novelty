package com.example.novelty.bean;

public class RequestBean {
    private BookBean book;
    private String status;
    private UserBean from;
    private UserBean ower;
    private GeoLocation location;

    public RequestBean(String status, UserBean from) {
        this.status = status;
        this.from = from;
    }

    public BookBean getBook() {
        return book;
    }

    public String getStatus() {
        return status;
    }

    public UserBean getFrom() {
        return from;
    }

    public void setFrom(UserBean from) {
        this.from = from;
    }

    public UserBean getOwer() {
        return ower;
    }

    public GeoLocation getLocation() {
        return location;
    }
}
