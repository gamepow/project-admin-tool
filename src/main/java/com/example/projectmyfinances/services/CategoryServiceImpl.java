package com.example.projectmyfinances.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.CategoryDTO;
import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategoriesByUser(int userId) {
        return categoryRepository.findAllCategoriesByUser(new User(userId));
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    private boolean doesCategoryExistForUser(String categoryName, int userId) {
        return categoryRepository.findAllCategoriesByUser(new User(userId))
                .stream()
                .anyMatch(category -> category.getCategoryName().equalsIgnoreCase(categoryName));
    }

    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        if (doesCategoryExistForUser(categoryDTO.getCategoryName(), categoryDTO.getUserId())) {
            throw new RuntimeException("Category already exists for the user.");
        }

        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryType(categoryDTO.getCategoryType());
        category.setUser(new User(categoryDTO.getUserId()));
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(int categoryId, CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setCategoryType(categoryDTO.getCategoryType());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
