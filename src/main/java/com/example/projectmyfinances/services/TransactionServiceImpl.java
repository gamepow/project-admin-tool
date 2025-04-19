package com.example.projectmyfinances.services;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.TransactionDTO;
import com.example.projectmyfinances.entities.Transaction;
import com.example.projectmyfinances.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setCategoryId(transaction.getCategory().getCategoryId());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getTransactionDescription());
        dto.setTransactionDate(transaction.getTransactionDate().toString());
        dto.setUserId(transaction.getUser().getUserId());
        dto.setCurrency(transaction.getCurrency());
        return dto;
    }

    @Override
    public List<Map<String, Object>> getTransactionExpensesSummaryByUser(int userId) {
        List<Object[]> results = transactionRepository.getTransactionExpensesSummaryByUser(userId);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("label", result[0]);
            map.put("value", result[1]);
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getTransactionIncomeSummaryByUser(int userId) {
        List<Object[]> results = transactionRepository.getTransactionIncomeSummaryByUser(userId);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("label", result[0]);
            map.put("value", result[1]);
            return map;
        }).collect(Collectors.toList());
    }
    
}
