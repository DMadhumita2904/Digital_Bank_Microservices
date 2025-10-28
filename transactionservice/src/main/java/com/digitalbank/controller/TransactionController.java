package com.digitalbank.controller;

import com.digitalbank.model.Transaction;
import com.digitalbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public Transaction transferMoney(@RequestParam Long fromAccountId,
                                     @RequestParam Long toAccountId,
                                     @RequestParam Double amount) {
        return transactionService.transferMoney(fromAccountId, toAccountId, amount);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
