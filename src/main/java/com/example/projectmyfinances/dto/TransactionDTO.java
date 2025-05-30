package com.example.projectmyfinances.dto;

public class TransactionDTO {
    private Integer transactionId;
    private String transactionType;
    private Integer categoryId;
    private String categoryName;
    private Integer accountId;
    private String accountName;
    private Double amount;
    private String description;
    private String transactionDate;
    private Integer userId;
    private String currency;

    public TransactionDTO() {
        // Default constructor
    }

    //Constructor for creating a new transaction
    public TransactionDTO(Integer transactionId, String transactionType, Integer categoryId, String categoryName, Double amount, String description, String transactionDate, Integer userId, String currency, Integer accountId, String accountName) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.accountId = accountId;
        this.accountName = accountName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
