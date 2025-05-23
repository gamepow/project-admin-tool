package com.example.projectmyfinances.services;

import java.util.List;
import java.util.Map;

import com.example.projectmyfinances.dto.TransactionDTO;
import com.example.projectmyfinances.entities.Transaction;

public interface TransactionService {

    Transaction createTransaction(TransactionDTO transactionDTO) throws Exception;
    List<TransactionDTO> getTransactionsByUserId(int userId);
    List<Map<String, Object>> getTransactionExpensesSummaryByUser(int userId);
    List<Map<String, Object>> getTransactionIncomeSummaryByUser(int userId);
    void deleteTransaction(int transactionId);
    List<TransactionDTO> getTransactionsByDateRange(int userId, String startDate, String endDate);
    List<Map<String, Object>> getMonthlyExpensesSummary(int userId, int year, int month);
    List<Map<String, Object>> getMonthlyIncomeSummary(int userId, int year, int month);

}
