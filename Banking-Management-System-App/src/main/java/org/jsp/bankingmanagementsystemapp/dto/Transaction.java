package org.jsp.bankingmanagementsystemapp.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private String transcationType;

	@Column(nullable = false)
	private double amount;

	@Column(nullable = false, unique = true)
	private long transactionId;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

//	@PrePersist
//	public long genrateUniqueTransactionId() {
//		this.transactionId = (long) (Math.random() * 9000000000L) + 1000000000L;
//		return this.transactionId;
//	}
	
	@PrePersist
	private void generateUniqueNumbers() {
		this.transactionId = genrateUniqueTransactionId();
		
	}

	private Long genrateUniqueTransactionId() {
		// Use a more secure method for generating unique IDs
		// Example: return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
		return (long) (Math.random() * 9000000000L) + 1000000000L;
	}
	
	
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTranscationType() {
		return transcationType;
	}

	public void setTranscationType(String transcationType) {
		this.transcationType = transcationType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

}
