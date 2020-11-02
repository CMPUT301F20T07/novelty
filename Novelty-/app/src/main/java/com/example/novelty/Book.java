package com.example.novelty;

import android.media.Image;

import java.util.ArrayList;

public class Book {
    private String title;
    private String author;
    private String description;
    private String status;
    private User owner;
    private User borrower;
    private ArrayList<Request> requests;
    private Image photograph;

    public Book(String title, String author, String description, String status, User owner, User borrower, ArrayList<Request> requests, Image photograph) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.status = status;
        this.owner = owner;
        this.borrower = borrower;
        this.requests = requests;
        this.photograph = photograph;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Image getPhotograph() {
        return photograph;
    }

    public void setPhotograph(Image photograph) {
        this.photograph = photograph;
    }

}
