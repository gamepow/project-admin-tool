package com.example.projectmyfinances.services;

import java.util.List;
import java.util.Map;

import com.example.projectmyfinances.entities.Transaction;
import com.example.projectmyfinances.dto.TransactionDTO;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);
    List<TransactionDTO> getTransactionsByUserId(int userId);
    List<Map<String, Object>> getTransactionExpensesSummaryByUser(int userId);
    List<Map<String, Object>> getTransactionIncomeSummaryByUser(int userId);

}
