package com.example.projectadmin.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectadmin.entities.Category;
import com.example.projectadmin.services.CategoryService;


@RestController
@RequestMapping("/api/private/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, List<Category>>> findAllCategoriesByUser(@PathVariable int userId) {
        List<Category> categories = categoryService.findAllCategoriesByUser(userId);
        Map<String, List<Category>> response = Map.of("data", categories);
        return ResponseEntity.ok(response);

    }
    

}
