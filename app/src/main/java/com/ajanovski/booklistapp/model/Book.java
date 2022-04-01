package com.ajanovski.booklistapp.model;

public class Book {
    private String name;
    private String author;
    private int insertedByUser;

    public Book(String name, String author, int insertedByUser) {
        this.name = name;
        this.author = author;
        this.insertedByUser = insertedByUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getInsertedByUser() {
        return insertedByUser;
    }

    public void setInsertedByUser(int insertedByUser) {
        this.insertedByUser = insertedByUser;
    }
}
