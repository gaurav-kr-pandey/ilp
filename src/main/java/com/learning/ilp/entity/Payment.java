package com.learning.ilp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int paymentId;
	private long courseId;
	private String courseTitle;
	@OneToOne(cascade = CascadeType.ALL)
	private Transaction transaction;
	private long amountPaid;
	private long actualAmount;
	private long balancedAmount;
	
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public long getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(long amountPaid) {
		this.amountPaid = amountPaid;
	}
	public long getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(long actualAmount) {
		this.actualAmount = actualAmount;
	}
	public long getBalancedAmount() {
		return balancedAmount;
	}
	public void setBalancedAmount(long balancedAmount) {
		this.balancedAmount = balancedAmount;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
		
}
