package com.example.projectadmin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectadmin.entities.Category;
import com.example.projectadmin.services.CategoryService;


@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Category>> findAllCategoriesByUser(@PathVariable Integer userId) {
        List<Category> categories = categoryService.findAllCategoriesByUser(userId);

        return ResponseEntity.ok(categories);

    }
    

}
