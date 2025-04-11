package com.example.projectadmin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectadmin.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // Custom query methods can be defined here if needed
    // For example, find transactions by user or category, etc.

    List<Transaction> findByUserId(Integer userId);

}