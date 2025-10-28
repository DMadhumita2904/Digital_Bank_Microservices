package com.digitalbank.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.digitalbank.model.Account;
import com.digitalbank.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PutMapping("/debit/{accountId}/{amount}")
public void debit(@PathVariable Long accountId, @PathVariable Double amount) {
    accountService.debit(accountId, amount);
}

@PutMapping("/credit/{accountId}/{amount}")
public void credit(@PathVariable Long accountId, @PathVariable Double amount) {
    accountService.credit(accountId, amount);
}

}
