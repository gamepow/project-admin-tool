package com.example.projectmyfinances.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.BudgetDTO;
import com.example.projectmyfinances.entities.Budget;
import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.entities.User;
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
    public Budget addBudget(BudgetDTO budget) {
        Budget newBudget = new Budget();
        newBudget.setCurrency(budget.getCurrency());
        newBudget.setBudgetAmount(budget.getBudgetAmount());
        newBudget.setUser(new User(budget.getUserId()));
        newBudget.setCategory(new Category(budget.getCategoryId()));

        return budgetRepository.save(newBudget);
    }
}
