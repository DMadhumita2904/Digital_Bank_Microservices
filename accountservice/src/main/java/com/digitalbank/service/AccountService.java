package com.digitalbank.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.digitalbank.model.Account;
import com.digitalbank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.digitalbank.model.CustomerDTO;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

public List<Account> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    RestTemplate restTemplate = new RestTemplate();

    for (Account account : accounts) {
        try {
            String url = "http://localhost:8080/api/customers/" + account.getCustomerId();
            CustomerDTO customer = restTemplate.getForObject(url, CustomerDTO.class);

            if (customer != null) {
                account.setFirstName(customer.getFirstName());
                account.setLastName(customer.getLastName());
                account.setEmail(customer.getEmail());
                account.setPhoneNumber(customer.getPhoneNumber());
            } else {
                account.setFirstName("Unknown");
                account.setLastName("");
            }
        } catch (Exception e) {
            account.setFirstName("Unknown");
            account.setLastName("");
        }
    }

    return accounts;
}


    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    // ✅ Combined & corrected method
    public Account createAccount(Account account) {
        // Call Customer Service to verify customer exists
        String url = "http://localhost:8080/api/customers/" + account.getCustomerId();
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return accountRepository.save(account);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Customer ID");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Customer ID");
        }
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public void debit(Long accountId, Double amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new RuntimeException("Account not found"));
    if (account.getBalance() < amount) {
        throw new RuntimeException("Insufficient balance");
    }
    account.setBalance(account.getBalance() - amount);
    accountRepository.save(account);
}

public void credit(Long accountId, Double amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new RuntimeException("Account not found"));
    account.setBalance(account.getBalance() + amount);
    accountRepository.save(account);
}

}
