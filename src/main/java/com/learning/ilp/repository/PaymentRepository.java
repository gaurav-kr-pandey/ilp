package com.learning.ilp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	public Payment findById(int paymentId);
}
