package com.example.projectadmin.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.projectadmin.entities.Category;
import com.example.projectadmin.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategoriesByUser(Integer userId){
        return categoryRepository.findAllCategoriesByUser(userId);
    }

}
