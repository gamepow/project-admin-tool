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
import com.example.projectmyfinances.entities.Account;
import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.entities.Transaction;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    public static Date parseStringToSqlDate(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format", e);
        }
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        // Set the creation and update timestamps
        // 1. Fetch the Category object from the database
        Category category = categoryService.getCategoryById(transactionDTO.getCategoryId());
        Account account = accountService.getAccountById(transactionDTO.getAccountId());
        Optional<User> optionalUser = userService.findById(transactionDTO.getUserId());

        if (!optionalUser.isPresent()) {
            throw new Exception("User not found");
        }

        if (category == null) {
            throw new Exception("Category not found");
        }

        if (account == null) {
            throw new Exception("Account not found");
        }

        User usuario = optionalUser.get();
        // 2. Convert the date string to java.sql.Date
        String pattern1 = "yyyy-MM-dd"; // Adjust the pattern as per your date format
        Date sqlDate1 = parseStringToSqlDate(transactionDTO.getTransactionDate(), pattern1);

        // 3. Create a new Transaction object and set its properties
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setCategory(category);
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDescription(transactionDTO.getDescription());
        transaction.setTransactionDate(sqlDate1);
        transaction.setUser(usuario);

        // Update account balance
        double newBalance = account.getCurrentBalance();
        if (transactionDTO.getTransactionType().equals("income")) {
            newBalance += transactionDTO.getAmount();
        } else {
            newBalance -= transactionDTO.getAmount();
        }
        accountService.updateAccountBalance(account.getAccountId(), newBalance);

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
            (String) row[8],  // currency
            (Integer) row[9], // accountId
            (String) row[10]  // accountName
        )).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public void deleteTransaction(int transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            Account account = transaction.getAccount();
            
            // Revert the account balance
            double newBalance = account.getCurrentBalance();
            if (transaction.getTransactionType().equals("income")) {
                newBalance -= transaction.getAmount();
            } else {
                newBalance += transaction.getAmount();
            }
            accountService.updateAccountBalance(account.getAccountId(), newBalance);
            
            transactionRepository.deleteById(transactionId);
        }
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

    @Override
    public List<TransactionDTO> getTransactionsByDateRange(int userId, String startDate, String endDate) {
        try {
            Date sqlStartDate = parseStringToSqlDate(startDate, "yyyy-MM-dd");
            Date sqlEndDate = parseStringToSqlDate(endDate, "yyyy-MM-dd");
            
            List<Object[]> results = transactionRepository.findByUserIdAndDateRange(userId, sqlStartDate, sqlEndDate);
            
            List<TransactionDTO> dtos = results.stream().map(row -> new TransactionDTO(
                (Integer) row[0], // transactionId
                (String) row[1],  // transactionType
                (Integer) row[2], // categoryId
                (String) row[3],  // categoryName
                row[4] != null ? ((Number) row[4]).doubleValue() : 0.0, // amount as double
                (String) row[5],  // description
                row[6] != null ? row[6].toString() : null, // transactionDate as String
                (Integer) row[7], // userId
                (String) row[8],  // currency
                (Integer) row[9], // accountId
                (String) row[10]  // accountName
            )).collect(Collectors.toList());

            return dtos;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving transactions by date range", e);
        }
    }

    @Override
    public List<Map<String, Object>> getMonthlyExpensesSummary(int userId, int year, int month) {
        return transactionRepository.getTransactionSummaryByTypeAndMonth(userId, "expense", year, month);
    }

    @Override
    public List<Map<String, Object>> getMonthlyIncomeSummary(int userId, int year, int month) {
        return transactionRepository.getTransactionSummaryByTypeAndMonth(userId, "income", year, month);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setCategoryId(transaction.getCategory().getCategoryId());
        dto.setCategoryName(transaction.getCategory().getCategoryName());
        dto.setAccountId(transaction.getAccount().getAccountId());
        dto.setAccountName(transaction.getAccount().getAccountName());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getTransactionDescription());
        dto.setTransactionDate(transaction.getTransactionDate().toString());
        dto.setUserId(transaction.getUser().getUserId());
        dto.setCurrency(transaction.getAccount().getCurrency());
        return dto;
    }
}
