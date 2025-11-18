package com.example.appregistrogastos;

public class CategorySummary {
    private String categoryName;
    private double amount;

    public CategorySummary(String categoryName, double amount) {
        this.categoryName = categoryName;
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getAmount() {
        return amount;
    }
}

