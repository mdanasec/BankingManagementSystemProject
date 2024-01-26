package org.jsp.bankingmanagementsystemapp.service;

import java.util.List; 

import org.jsp.bankingmanagementsystemapp.dao.TransactionDao;
import org.jsp.bankingmanagementsystemapp.dto.ResponseStructure;
import org.jsp.bankingmanagementsystemapp.dto.Transaction;
import org.jsp.bankingmanagementsystemapp.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	@Autowired
	private TransactionDao transactionDao;

	public ResponseEntity<ResponseStructure<Transaction>> saveTransaction(Transaction transaction) {
		ResponseStructure<Transaction> structure = new ResponseStructure<>();

		structure.setData(transactionDao.saveTransaction(transaction));
		structure.setMessage("Transaction Saved Sucessfully ");
		structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Transaction>>(structure, HttpStatus.CREATED);

	}
	
	public ResponseEntity<ResponseStructure<List<Transaction>>> findByCifNumber(Long cifNumber){
		ResponseStructure<List<Transaction>> structure = new ResponseStructure<>();
		List<Transaction> recTransaction = transactionDao.findByCifNumber(cifNumber);
		
		if(!recTransaction.isEmpty()) {
			structure.setMessage("Transaction Found");
			structure.setData(recTransaction);
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Transaction>>> (structure , HttpStatus.OK);
		}
		throw new TransactionNotFoundException("Transaction Not Found ");
	}

}
