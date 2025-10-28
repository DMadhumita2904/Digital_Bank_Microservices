package com.digitalbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digitalbank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
