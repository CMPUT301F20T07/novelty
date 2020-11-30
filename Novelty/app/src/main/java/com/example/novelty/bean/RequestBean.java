package com.example.novelty.bean;

import java.util.List;

public class RequestBean {
    private BookBean book;
    private String status;
    private List<UserBean> from;
    private UserBean ower;
    private GeoLocation location;

    public RequestBean(String status, List<UserBean> from) {
        this.status = status;
        this.from = from;
    }

    public BookBean getBook() {
        return book;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFrom(List<UserBean> from) {
        this.from = from;
    }

    public List<UserBean> getFrom() {
        return from;
    }

    public UserBean getOwer() {
        return ower;
    }

    public GeoLocation getLocation() {
        return location;
    }
}
