package com.example.projectmyfinances.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectmyfinances.entities.Account;
import com.example.projectmyfinances.entities.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.isActive = true")
    List<Account> findAllActiveAccountsByUser(@Param("user") User user);

    @Query("SELECT a FROM Account a WHERE a.user = :user")
    List<Account> findAllAccountsByUser(@Param("user") User user);
}
