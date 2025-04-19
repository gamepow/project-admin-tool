package com.example.projectmyfinances.dto;

public class TransactionDTO {
    private Integer transactionId;
    private String transactionType;
    private Integer categoryId;
    private double amount;
    private String description;
    private String transactionDate;
    private Integer userId;
    private String currency;

    public TransactionDTO() {
        // Default constructor
    }

    //Constructor for creating a new transaction
    public TransactionDTO(Integer transactionId, String transactionType, Integer categoryId, double amount, String description, String transactionDate, int userId, String currency) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.userId = userId;
        this.currency = currency;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Getters and setters
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
