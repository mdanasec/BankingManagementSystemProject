package org.jsp.bankingmanagementsystemapp.controller;

import java.util.List;

import org.jsp.bankingmanagementsystemapp.dto.ResponseStructure;
import org.jsp.bankingmanagementsystemapp.dto.Transaction;
import org.jsp.bankingmanagementsystemapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/save")
	public ResponseEntity<ResponseStructure<Transaction>> saveTransaction(@RequestBody Transaction transaction) {
		return transactionService.saveTransaction(transaction);
	}

	@GetMapping(value = "/getTransaction/{cifNumber}")
	
	public ResponseEntity<ResponseStructure<List<Transaction>>> findByCifNumber(@PathVariable Long cifNumber) {
		return transactionService.findByCifNumber(cifNumber);
	}

}
