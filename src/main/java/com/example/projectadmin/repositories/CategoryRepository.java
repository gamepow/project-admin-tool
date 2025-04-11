package com.example.projectadmin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectadmin.entities.Category;
import com.example.projectadmin.entities.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
    //Custom query to retrieve all public categories and user-specific 
    @Query("SELECT c FROM Category c WHERE c.user = :userId OR c.user IS NULL")
    List<Category> findAllCategoriesByUser(@Param("userId") User user);
} 