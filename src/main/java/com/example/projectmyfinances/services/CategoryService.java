package com.example.projectmyfinances.services;

import java.util.List;

import com.example.projectmyfinances.dto.CategoryDTO;
import com.example.projectmyfinances.entities.Category;

public interface CategoryService {
    List<Category> findAllCategoriesByUser(int userId);
    Category getCategoryById(Integer categoryId);
    Category addCategory(CategoryDTO categoryDTO);
    Category updateCategory(int categoryId, CategoryDTO categoryDTO);
    void deleteCategory(int categoryId);
}
