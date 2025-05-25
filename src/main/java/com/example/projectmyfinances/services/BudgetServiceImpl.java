package com.example.projectmyfinances.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.entities.Budget;
import com.example.projectmyfinances.repositories.BudgetRepository;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public List<Budget> findAllBudgetsByUser(int userId) {
        return budgetRepository.findAllByUserId(userId);
    }

    @Override
    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
}
