package com.example.moneysaving.object;


import com.google.firebase.Timestamp;

public class Expense {
    private String id;
    private double amount;
    private String category;
    private String note;
    private String type;
    private Timestamp date;
    private Timestamp createdAt;

    public Expense() {}

    public Expense(double amount, String category, String note, String type, Timestamp date) {
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.type = type;
        this.date = date;
        this.createdAt = Timestamp.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
