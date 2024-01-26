package org.jsp.bankingmanagementsystemapp.controller;

import java.util.List;
import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dto.Customer;
import org.jsp.bankingmanagementsystemapp.dto.ResponseStructure;
import org.jsp.bankingmanagementsystemapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/customers/{adminId}")
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody Customer customer,
			@PathVariable int adminId) {
		return customerService.saveCustomer(customer, adminId);
	}

	@PutMapping(value = "/customers")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@RequestBody Customer customer) {
		return customerService.updateCustomer(customer);
	}
	
	@PutMapping(value = "/customerUpdate/{id}")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomerById(@RequestBody Customer customer, @PathVariable long id){
		return customerService.updateCustomerById(customer, id);
	}
	
	@GetMapping(value = "/customers/{id}")
	public ResponseEntity<ResponseStructure<Optional<Customer>>> findById(@PathVariable long id) {
		return customerService.findById(id);
	}

	@PostMapping(value = "/customer/verifyByCifNumber")
	public ResponseEntity<ResponseStructure<Optional<Customer>>> verifyByCifNo(@RequestParam long cifNumber,
			@RequestParam String password) {
		return customerService.verifyByCifNumber(cifNumber, password);
	}

	@GetMapping(value = "/customer/cifnumber/{cifNumber}")
	public ResponseEntity<ResponseStructure<Optional<Customer>>> getDataByCifNumber(@PathVariable long cifNumber) {
		return customerService.getDataByCifNumber(cifNumber);
	}

	@GetMapping(value = "/customer/{accountNumber}")
	public ResponseEntity<ResponseStructure<Customer>> findByAccountNumber(@PathVariable long accountNumber) {
		return customerService.findByAccountNumber(accountNumber);
	}

	@PostMapping("/customer/sendMoney")
	public ResponseEntity<ResponseStructure<Optional<Customer>>> sendMoneyByAccountNo(
			@RequestParam long senderAccountNumber, @RequestParam String password, @RequestParam String senderIfscCode,
			@RequestParam long receiverAccountNumber, @RequestParam double sendAmount) {

		return customerService.sendMoneyByAccountNo(senderAccountNumber, password, senderIfscCode,
				receiverAccountNumber, sendAmount);
	}
	
	@GetMapping(value = "/getAllCustomers")
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers(){
		return customerService.getAllCustomers();
	}

}
