package com.example.projectmyfinances.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.entities.Budget;
import com.example.projectmyfinances.services.BudgetService;

@RestController
@RequestMapping("/api/private/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> findAllBudgetsByUser(@PathVariable int userId) {
        try {
            List<Budget> budgets = budgetService.findAllBudgetsByUser(userId);
            return ResponseEntity.ok(budgets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch budgets.");
        }
    }

    @PostMapping
    public ResponseEntity<?> addBudget(@RequestBody Budget budget) {
        try {
            Budget newBudget = budgetService.addBudget(budget);
            return ResponseEntity.ok(newBudget);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add budget.");
        }
    }
}
