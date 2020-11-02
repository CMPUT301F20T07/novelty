package com.example.novelty.bean;

import java.util.ArrayList;

public class UserBean {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String identity;

    public UserBean() {
    }

    public UserBean(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setMyBookz(ArrayList<BookBean> myBookz) {
        this.myBookz = myBookz;
    }

    public void setBorrowedBooks(ArrayList<BookBean> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdentity() {
        return identity;
    }

    public ArrayList<BookBean> getMyBookz() {
        return myBookz;
    }

    public ArrayList<BookBean> getBorrowedBooks() {
        return borrowedBooks;
    }

    private ArrayList<BookBean> myBookz;
    private ArrayList<BookBean> borrowedBooks;
}
