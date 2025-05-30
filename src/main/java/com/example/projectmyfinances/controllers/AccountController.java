package com.example.projectmyfinances.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.dto.AccountDTO;
import com.example.projectmyfinances.entities.Account;
import com.example.projectmyfinances.services.AccountService;

@RestController
@RequestMapping("/api/private/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> findAllAccountsByUser(@PathVariable int userId) {
        try {
            List<Account> accounts = accountService.findAllAccountsByUser(userId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch accounts.");
        }
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<?> findAllActiveAccountsByUser(@PathVariable int userId) {
        try {
            List<Account> accounts = accountService.findAllActiveAccountsByUser(userId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch active accounts.");
        }
    }
    
    @PostMapping
    public ResponseEntity<?> addAccount(@RequestBody AccountDTO account) {
        try {
            Account newAccount = accountService.addAccount(account);
            return ResponseEntity.ok(newAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add account.");
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable int accountId, @RequestBody AccountDTO account) {
        try {
            Account updatedAccount = accountService.updateAccount(accountId, account);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update account.");
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable int accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Account deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete account.");
        }
    }
}
