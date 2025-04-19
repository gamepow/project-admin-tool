package com.example.projectmyfinances.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategoriesByUser(int userId){
        return categoryRepository.findAllCategoriesByUser(new User(userId));
    }

    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

}
