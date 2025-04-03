package com.example.projectadmin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projectadmin.entities.Category;
import com.example.projectadmin.entities.UserProfile;

@Repository
public interface CategoryRepository extends JpaRepository<UserProfile, Integer>{
    
    //Custom query to retrieve all public categories and user-specific 
    @Query("SELECT c from Category c WHERE c.user is null or c.user = :userId")
    List<Category> findAllCategoriesByUser(Integer userId);
} 