package org.jsp.bankingmanagementsystemapp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dao.AdminDao;
import org.jsp.bankingmanagementsystemapp.dao.CustomerDao;
import org.jsp.bankingmanagementsystemapp.dao.TransactionDao;
import org.jsp.bankingmanagementsystemapp.dto.Admin;
import org.jsp.bankingmanagementsystemapp.dto.Customer;
import org.jsp.bankingmanagementsystemapp.dto.ResponseStructure;
import org.jsp.bankingmanagementsystemapp.dto.Transaction;
import org.jsp.bankingmanagementsystemapp.exception.AdminNotFoundException;
import org.jsp.bankingmanagementsystemapp.exception.CustomerNotFoundException;
import org.jsp.bankingmanagementsystemapp.exception.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private TransactionDao transactionDao;

	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(Customer customer, int adminId) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Optional<Admin> recAdmin = adminDao.findById(adminId);

		if (recAdmin.isPresent()) {
			Admin admin = recAdmin.get();
			admin.getCustomer().add(customer);
			customer.setAdmin(admin);
			adminDao.saveAdmin(admin);
			structure.setData(customerDao.saveCustomer(customer));
			structure.setMessage("Customer Added Sucessfully");
			structure.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Customer>>(structure, HttpStatus.CREATED);

		}
		throw new AdminNotFoundException("Admin not found " + adminId);
	}

	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(Customer customer) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Optional<Customer> recCustomer = customerDao.findById(customer.getId());
		if (recCustomer.isPresent()) {
			structure.setMessage("Customer Updated Successfully");
			structure.setData(customerDao.updateCustomer(customer));
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Customer>>(structure, HttpStatus.OK);
		}
		throw new CustomerNotFoundException("Customer Not Found ");
	}

// Update Customer By Id API
	public ResponseEntity<ResponseStructure<Customer>> updateCustomerById(Customer customer, long id){
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Optional<Customer> recCustomer = customerDao.findById(id);
		if(recCustomer.isPresent()) {
			Customer existCustomer = recCustomer.get();
			existCustomer.setFirstName(customer.getFirstName());
			existCustomer.setLastName(customer.getLastName());
			existCustomer.setPhone(customer.getPhone());
			existCustomer.setEmail(customer.getEmail());
			existCustomer.setGender(customer.getGender());
			existCustomer.setDateOfBirth(customer.getDateOfBirth());
			existCustomer.setOccupation(customer.getOccupation());
			existCustomer.setAccountType(customer.getAccountType());
			existCustomer.setAddress(customer.getAddress());
			existCustomer.setIfscCode(customer.getIfscCode());
			existCustomer.setPassword(customer.getPassword());
			
			 // Save the updated product
	        Customer updateCustomer = customerDao.saveCustomer(existCustomer);
	        structure.setData(updateCustomer);
	        structure.setMessage("Customer Updated Successfully");
	        structure.setStatusCode(HttpStatus.ACCEPTED.value());
	        return new ResponseEntity<>(structure, HttpStatus.ACCEPTED);
	    } 
	        // Product with the given ID not found
	        throw new CustomerNotFoundException("Customer not updated");
			
	}
	
//	Find By id API
	public ResponseEntity<ResponseStructure<Optional<Customer>>> findById(long id) {
		ResponseStructure<Optional<Customer>> structure = new ResponseStructure<>();
		Optional<Customer> recCustomer = customerDao.findById(id);

		if (recCustomer.isPresent()) {
			structure.setMessage("Customer Found");
			structure.setData(recCustomer);
			structure.setStatusCode(HttpStatus.ACCEPTED.value());

			return new ResponseEntity<ResponseStructure<Optional<Customer>>>(structure, HttpStatus.ACCEPTED);
		}
		throw new CustomerNotFoundException("Customer not Found ");
	}

//	Verify By CIF Number API

	public ResponseEntity<ResponseStructure<Optional<Customer>>> verifyByCifNumber(long cifNumber, String password) {
		ResponseStructure<Optional<Customer>> structure = new ResponseStructure<>();
		Optional<Customer> recCustomer = customerDao.verifyByCifNumber(cifNumber, password);
		if (recCustomer.isPresent()) {
			structure.setMessage("login Sucessfully ");
			structure.setData(recCustomer);
			structure.setStatusCode(HttpStatus.OK.value());

			return new ResponseEntity<ResponseStructure<Optional<Customer>>>(structure, HttpStatus.OK);
		}
		throw new CustomerNotFoundException("you have entered wrong email or password");
	}

//	Getting Data By CIF Number API

	public ResponseEntity<ResponseStructure<Optional<Customer>>> getDataByCifNumber(long cifNumber) {
		ResponseStructure<Optional<Customer>> structure = new ResponseStructure<>();
		Optional<Customer> recCustomer = customerDao.getDataByCifNumber(cifNumber);
		if (recCustomer.isPresent()) {
			structure.setMessage("Data Found ");
			structure.setData(recCustomer);
			structure.setStatusCode(HttpStatus.OK.value());

			return new ResponseEntity<ResponseStructure<Optional<Customer>>>(structure, HttpStatus.OK);
		}
		throw new CustomerNotFoundException("Data Not Found");
	}

//	Find By Account Number API

	public ResponseEntity<ResponseStructure<Customer>> findByAccountNumber(long accountNumber) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		Optional<Customer> recCustomer = customerDao.findByAccountNumber(accountNumber);
		if (recCustomer.isPresent()) {
			structure.setMessage("Account Found ");
			structure.setData(recCustomer.get());
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Customer>>(structure, HttpStatus.OK);
		}
		throw new AdminNotFoundException("Account Not Found ");
	}

//	Send Money API

	@Transactional
	public ResponseEntity<ResponseStructure<Optional<Customer>>> sendMoneyByAccountNo(long senderAccountNumber,
			String password, String senderIfscCode, long receiverAccountNumber, double sendAmount) {

		ResponseStructure<Optional<Customer>> structure = new ResponseStructure<>();

		Optional<Customer> senderOptional = customerDao.sendMoney(senderAccountNumber, password, senderIfscCode);

		if (senderOptional.isPresent()) {
			Customer sender = senderOptional.get();

			if (sendAmount <= sender.getBalance()) {
				// Deduct the amount from the sender's balance
				customerDao.updateBalance(senderAccountNumber, sender.getBalance() - sendAmount);

				// Find the receiving customer
				Optional<Customer> receiverOptional = customerDao.findByAccountNumber(receiverAccountNumber);

				if (receiverOptional.isPresent()) {
					Customer receiver = receiverOptional.get();

					// Update the balance of the receiving customer
					customerDao.updateBalance(receiverAccountNumber, receiver.getBalance() + sendAmount);

					// Create and save a transaction record for the sender
					Transaction senderTransaction = new Transaction();
					senderTransaction.setCustomer(sender);
					senderTransaction.setDate(new Date());
					senderTransaction.setTranscationType("SEND");
					senderTransaction.setAmount(sendAmount);

					// You may set a unique transaction ID if needed.
					// Save the transaction record
					transactionDao.saveTransaction(senderTransaction);

					// Create and save a transaction record for the receiver
					Transaction receiverTransaction = new Transaction();
					receiverTransaction.setCustomer(receiver);
					receiverTransaction.setDate(new Date());
					receiverTransaction.setTranscationType("RECEIVE");
					receiverTransaction.setAmount(sendAmount);

					// You may set a unique transaction ID if needed.
					// Save the transaction record
					transactionDao.saveTransaction(receiverTransaction);

					structure.setMessage("Amount Sent Successfully");
					structure.setData(senderOptional);
					structure.setStatusCode(HttpStatus.ACCEPTED.value());

					return new ResponseEntity<>(structure, HttpStatus.ACCEPTED);
				} else {
					throw new CustomerNotFoundException("Receiver Account Not Found");
				}
			} else {
				throw new InsufficientBalanceException("Insufficient balance: " + sender.getBalance());
			}
		}
		else 
			throw new CustomerNotFoundException("Sender Account Not Found");
	}
	
//	Get All Customer API 
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers(){
		ResponseStructure<List<Customer>> structure = new ResponseStructure<>();
		List<Customer> recCustomer = customerDao.getAllCustomers();
		if(!recCustomer.isEmpty()) {
			structure.setMessage("Customers Found ");
			structure.setData(recCustomer);
			structure.setStatusCode(HttpStatus.OK.value());
			
			return new ResponseEntity<ResponseStructure<List<Customer>>> (structure, HttpStatus.OK);
			
		}
		throw new CustomerNotFoundException("Customers Not Found !!");
	}
	
}































