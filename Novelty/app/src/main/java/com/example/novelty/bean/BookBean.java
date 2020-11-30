package com.example.novelty.bean;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class BookBean implements Serializable {
    String title;
    String author;
    String ISBN;
    String description;
    String status ="request";
    String owner;
    String borrower;
    String holder;
    String imageID;
    Uri photoUri;

    private ArrayList<RequestBean> requests;

    public BookBean(String title) {
        this.title = title;
        this.author = null;
        this.ISBN = null;
        this.description = null;
        this.owner = null;
        this.borrower = null;
        this.holder = null;
        this.owner = null;
        this.photoUri = null;
        this.status = null;
        this.imageID = "";
    }

    public BookBean(String title, String description, String status, String owner) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.owner = owner;
        this.ISBN = null;
        this.borrower = null;
        this.holder = null;
        this.photoUri = null;
        this.status = null;
        this.imageID = "";
    }


    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
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

}
