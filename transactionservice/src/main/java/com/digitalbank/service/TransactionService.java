package com.digitalbank.service;

import com.digitalbank.model.Transaction;
import com.digitalbank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    private final String ACCOUNT_SERVICE_URL = "http://localhost:8081/api/accounts";
    private final String FRAUD_SERVICE_URL = "http://localhost:5000/predict";

    /**
     * Executes a money transfer between two accounts.
     * Checks with the Fraud Detection Service before processing.
     */
    public Transaction transferMoney(Long fromAccountId, Long toAccountId, Double amount) {
        String status;
        Transaction tx;

        try {
            // 1️⃣ Prepare fraud detection request body
            Map<String, Object> request = new HashMap<>();
            request.put("fromAccountId", fromAccountId);
            request.put("toAccountId", toAccountId);
            request.put("amount", amount);
            request.put("timestamp", LocalDateTime.now().toString());

            // 2️⃣ Send request to Fraud Detection Microservice
            ResponseEntity<Map> response = restTemplate.postForEntity(FRAUD_SERVICE_URL, request, Map.class);
            Map<String, Object> fraudResult = response.getBody();

            System.out.println("Fraud Detection Result: " + fraudResult);

            String decision = (String) fraudResult.get("decision");

            if (decision.equalsIgnoreCase("fraud")) {
                // 🚨 Fraud detected → Do NOT process debit/credit
                status = "FAILED";
            } else {
                // ✅ Safe transaction → Proceed with debit and credit
                restTemplate.put(ACCOUNT_SERVICE_URL + "/debit/{accountId}/{amount}", null, fromAccountId, amount);
                restTemplate.put(ACCOUNT_SERVICE_URL + "/credit/{accountId}/{amount}", null, toAccountId, amount);
                status = "SUCCESS";
            }

        } catch (Exception e) {
            System.err.println("Transaction failed: " + e.getMessage());
            status = "FAILED";
        }

        // 3️⃣ Save transaction in DB
        tx = Transaction.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(tx);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
