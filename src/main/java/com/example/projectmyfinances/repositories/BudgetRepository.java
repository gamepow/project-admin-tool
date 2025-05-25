package com.example.projectmyfinances.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectmyfinances.entities.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findAllByUserId(int userId);
}
