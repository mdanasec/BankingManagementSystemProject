package org.jsp.bankingmanagementsystemapp.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dto.Transaction;
import org.jsp.bankingmanagementsystemapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDao {

	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	public Optional<Transaction> findById(int id) {
		return transactionRepository.findById(id);
	}

	public List<Transaction> findByCifNumber(Long cifNumber) {
		return transactionRepository.findByCifNumber(cifNumber);

	}

}
