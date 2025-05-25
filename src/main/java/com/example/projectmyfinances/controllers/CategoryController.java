package com.example.projectmyfinances.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectmyfinances.dto.CategoryDTO;
import com.example.projectmyfinances.entities.Category;
import com.example.projectmyfinances.services.CategoryServiceImpl;


@RestController
@RequestMapping("/api/private/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> findAllCategoriesByUser(@PathVariable int userId) {
        List<Category> categories = categoryService.findAllCategoriesByUser(userId);
        return ResponseEntity.ok(categories);

    }
    
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO category) {
        try {
            // Ensure categoryType is validated or logged
            if (category.getCategoryType() == null || category.getCategoryType().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category type is required.");
            }
            Category newCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(newCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add category.");
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable int categoryId, @RequestBody CategoryDTO category) {
        try {
            // Ensure categoryType is validated or logged
            if (category.getCategoryType() == null || category.getCategoryType().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category type is required.");
            }
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update category.");
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Category deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete category.");
        }
    }

}
