package com.example.novelty.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class BookBean implements Serializable {
    String title;
    String author;
    String ISBN;
    String description;
    String status;
    String owner;
    String borrower;
    String photograph;
    private ArrayList<RequestBean> requests;

    public BookBean(String title) {
        this.title = title;
    }

    public BookBean(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public ArrayList<RequestBean> getRequests() {
        return requests;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }
}
