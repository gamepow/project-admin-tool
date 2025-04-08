package com.example.projectadmin.entities;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName= "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName= "category_id")
    private Category category;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate; // Add this field

    @Column(name = "amount")
    private double amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "transaction_description")
    private String transactionDescription;

    @Column(name = "create_at")
    private Timestamp createDate;

    @Column(name = "update_at")
    private Timestamp updateDate;

    public Transaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
    
    
}
