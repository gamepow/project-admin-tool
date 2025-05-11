package com.example.projectmyfinances.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.dto.TransactionDTO;
import com.example.projectmyfinances.entities.Transaction;
import com.example.projectmyfinances.services.TransactionService;

@RestController
@RequestMapping("/api/private/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        try{
            Transaction createdTransaction = transactionService.createTransaction(transactionDTO);

            // Return a JSON object with the transaction ID and a message
            Map<String, Object> response = new HashMap<>();
            response.put("transactionId", createdTransaction.getTransactionId());
            response.put("message", "Transaction created successfully");

            return ResponseEntity.ok(response);
        }
        catch(Exception ex){;
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Request is not valid.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllUserTransactions(@PathVariable int userId) {

        try{
            // 1. Retrieve user's transactions from service
            List<TransactionDTO> transactions = transactionService.getTransactionsByUserId(userId);

            // 2. return the list of transactions as a JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("data", transactions);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex){
            System.out.println("Error retrieving transactions: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve transactions.");
        }
        
    }

    @GetMapping("/summary/expenses/{userId}")
    public ResponseEntity<?> getTransactionsExpensesSummary(@PathVariable int userId) {
        try {
            List<Map<String, Object>> transactionSummary = transactionService.getTransactionExpensesSummaryByUser(userId);
            return new ResponseEntity<>(transactionSummary, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve transactions summary.");
        }
    }

    @GetMapping("/summary/income/{userId}")
    public ResponseEntity<?> getTransactionsIncomeSummary(@PathVariable int userId) {
        try {
            List<Map<String, Object>> transactionSummary = transactionService.getTransactionIncomeSummaryByUser(userId);
            return new ResponseEntity<>(transactionSummary, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve transactions summary.");
        }
    }
    
    

}
