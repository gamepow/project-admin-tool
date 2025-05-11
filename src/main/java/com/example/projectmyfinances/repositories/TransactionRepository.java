package com.example.projectmyfinances.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectmyfinances.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // Custom query methods can be defined here if needed
    // For example, find transactions by user or category, etc.

    @Query(value = 
                    "SELECT t.transaction_id, UPPER(t.transaction_type), c.category_id, UPPER(c.category_name), t.amount, t.transaction_description, t.transaction_date, t.user_id, up.default_currency as currency " +
                    "FROM transactions t JOIN category c ON t.category_id = c.category_id " +
                    "JOIN userProfile up on t.user_id = up.user_id " +
                    "WHERE t.user_id = :userId", nativeQuery = true)
    List<Object[]> findByUserId(Integer userId);

    @Query(value = "SELECT c.category_name AS label, SUM(t.amount) AS value " +
                   "FROM transactions t JOIN category c ON t.category_id = c.category_id " +
                   "WHERE t.user_id = :userId and t.transaction_type = 'expense' GROUP BY c.category_name", nativeQuery = true)
    List<Object[]> getTransactionExpensesSummaryByUser(@Param("userId") int userId);

    @Query(value = "SELECT c.category_name AS label, SUM(t.amount) AS value " +
                   "FROM transactions t JOIN category c ON t.category_id = c.category_id " +
                   "WHERE t.user_id = :userId and t.transaction_type = 'income' GROUP BY c.category_name", nativeQuery = true)
    List<Object[]> getTransactionIncomeSummaryByUser(@Param("userId") int userId);

}