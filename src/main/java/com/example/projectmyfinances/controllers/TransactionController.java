package com.example.projectmyfinances.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.entities.Transaction;
import com.example.projectmyfinances.entities.TransactionDTO;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.entities.UserProfile;
import com.example.projectmyfinances.services.CategoryService;
import com.example.projectmyfinances.services.TransactionService;
import com.example.projectmyfinances.services.UserProfileService;
import com.example.projectmyfinances.services.UserService;


@RestController
@RequestMapping("/api/private/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    UserProfileService userProfileService;

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

    @PostMapping("/save")
    public ResponseEntity<?> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        try{
            // 1. Fetch the Category object from the database
            Category category = categoryService.getCategoryById(transactionDTO.getCategoryId());

            Optional<User> optionalUser = userService.findById(transactionDTO.getUserId());

            Optional<UserProfile> optionalUserProfile = userProfileService.findByUserId(transactionDTO.getUserId());

            if (!optionalUser.isPresent()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }

            if (category == null) {
                return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
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
            transaction.setCurrency(optionalUserProfile.get().getDefaultCurrency());

            // 4. Save the transaction using the TransactionService

            Transaction createdTransaction = transactionService.createTransaction(transaction);

            return ResponseEntity.ok("Transaction created successfully with ID: " + createdTransaction.getTransactionId());
        }
        catch(Exception ex){;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request is not valid.");
        }
        
    }
    

}
