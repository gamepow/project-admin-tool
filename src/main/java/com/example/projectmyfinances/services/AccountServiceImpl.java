package com.example.projectmyfinances.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectmyfinances.dto.AccountDTO;
import com.example.projectmyfinances.entities.Account;
import com.example.projectmyfinances.entities.User;
import com.example.projectmyfinances.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Account> findAllAccountsByUser(int userId) {
        return accountRepository.findAllAccountsByUser(new User(userId));
    }

    @Override
    public List<Account> findAllActiveAccountsByUser(int userId) {
        return accountRepository.findAllActiveAccountsByUser(new User(userId));
    }

    @Override
    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public Account addAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountName(accountDTO.getAccountName());
        account.setAccountType(accountDTO.getAccountType());
        account.setCurrency(accountDTO.getCurrency());
        account.setCurrentBalance(accountDTO.getCurrentBalance());
        account.setUser(new User(accountDTO.getUserId()));
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(int accountId, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));

        existingAccount.setAccountName(accountDTO.getAccountName());
        existingAccount.setAccountType(accountDTO.getAccountType());
        existingAccount.setCurrency(accountDTO.getCurrency());
        existingAccount.setCurrentBalance(accountDTO.getCurrentBalance());
        existingAccount.setActive(accountDTO.getIsActive());

        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(int accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setActive(false);
        accountRepository.save(account);
    }

    @Override
    public void updateAccountBalance(int accountId, double newBalance) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setCurrentBalance(newBalance);
        accountRepository.save(account);
    }
}
