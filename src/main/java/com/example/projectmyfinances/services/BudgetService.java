package com.example.projectmyfinances.services;

import java.util.List;

import com.example.projectmyfinances.entities.Budget;

public interface BudgetService {
    List<Budget> findAllBudgetsByUser(int userId);
    Budget addBudget(Budget budget);
}
