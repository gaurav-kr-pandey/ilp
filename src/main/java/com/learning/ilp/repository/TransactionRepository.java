package com.learning.ilp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	public Transaction findByTransactionId(String transactionId);
	
	public Transaction findById(int transactionKey);
	
	public boolean existsByTransactionId(String transactionId);
}
