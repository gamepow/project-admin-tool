package com.example.projectmyfinances.services;

import java.util.List;

import com.example.projectmyfinances.dto.AccountDTO;
import com.example.projectmyfinances.entities.Account;

public interface AccountService {
    List<Account> findAllAccountsByUser(int userId);
    List<Account> findAllActiveAccountsByUser(int userId);
    Account getAccountById(Integer accountId);
    Account addAccount(AccountDTO accountDTO);
    Account updateAccount(int accountId, AccountDTO accountDTO);
    void deleteAccount(int accountId);
    void updateAccountBalance(int accountId, double newBalance);
}
