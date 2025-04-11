package com.example.projectadmin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectadmin.entities.Category;
import com.example.projectadmin.entities.User;
import com.example.projectadmin.repositories.CategoryRepository;

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
