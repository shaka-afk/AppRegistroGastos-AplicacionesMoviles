package com.example.appregistrogastos;

public class Transaction {
    private String category;
    private String description;
    private double amount;
    private String date;
    private boolean isIncome;

    public Transaction(String category, String description, double amount, String date, boolean isIncome) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isIncome = isIncome;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public boolean isIncome() {
        return isIncome;
    }
}

