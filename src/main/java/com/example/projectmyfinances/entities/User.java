package com.example.projectmyfinances.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 3, max = 25, message = "Username must be between 3 and 50 characters")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 30, message = "Password must be at least 8 characters long")
    private String password;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "create_date",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createDate;

    @Column(name = "update_date",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updateDate;


    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createdAt) {
        this.createDate = createdAt;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updatedAt) {
        this.updateDate = updatedAt;
    }

    

}
