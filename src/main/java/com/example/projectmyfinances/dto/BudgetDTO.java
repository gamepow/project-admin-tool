package com.example.projectmyfinances.dto;

public class BudgetDTO {
    private int budgetId;
    private String currency;
    private Double budgetAmount;
    private int categoryId;
    private int userId;
    
    public BudgetDTO() {
    }

    public int getBudgetId() {
        return budgetId;
    }
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Double getBudgetAmount() {
        return budgetAmount;
    }
    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
}
