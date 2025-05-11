package com.example.projectmyfinances.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.TransactionDTO;
import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.entities.Transaction;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    public static Date parseStringToSqlDate(String dateString, String pattern) {
        try {
            // 1. Create a DateTimeFormatter based on the expected pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            // 2. Parse the string into a LocalDate
            LocalDate localDate = LocalDate.parse(dateString, formatter);

            // 3. Convert the LocalDate to a java.sql.Date
            return Date.valueOf(localDate);

        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date string: " + dateString + " with pattern: " + pattern);
            e.printStackTrace();
            return null; // Or throw an exception
        }
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {

        // Set the creation and update timestamps
        // 1. Fetch the Category object from the database
        Category category = categoryService.getCategoryById(transactionDTO.getCategoryId());

        Optional<User> optionalUser = userService.findById(transactionDTO.getUserId());

        if (!optionalUser.isPresent()) {
            throw new Exception("User not found");
        }

        if (category == null) {
            throw new Exception("Category not found");
        }

        User usuario = optionalUser.get();
        // 2. Convert the date string to java.sql.Date
        String pattern1 = "yyyy-MM-dd"; // Adjust the pattern as per your date format
        Date sqlDate1 = parseStringToSqlDate(transactionDTO.getTransactionDate(), pattern1);

        // 3. Create a new Transaction object and set its properties
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setCategory(category);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDescription(transactionDTO.getDescription());
        transaction.setTransactionDate(sqlDate1);
        transaction.setUser(usuario);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactionsByUserId(int userId) {
        List<Object[]> results = transactionRepository.findByUserId(userId);
        
        List<TransactionDTO> dtos = results.stream().map(row -> new TransactionDTO(
            (Integer) row[0], // transactionId
            (String) row[1],  // transactionType
            (Integer) row[2], // categoryId
            (String) row[3],  // categoryName
            row[4] != null ? ((Number) row[4]).doubleValue() : 0.0, // amount as double
            (String) row[5],  // description
            row[6] != null ? row[6].toString() : null, // transactionDate as String
            (Integer) row[7], // userId
            (String) row[8]   // currency
        )).collect(Collectors.toList());

        return dtos;
        
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
