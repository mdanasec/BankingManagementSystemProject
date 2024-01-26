package org.jsp.bankingmanagementsystemapp.dao;

import java.util.List;
import java.util.Optional; 

import org.jsp.bankingmanagementsystemapp.dto.Customer;
import org.jsp.bankingmanagementsystemapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

	@Autowired
	public CustomerRepository customerRepository;

	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer updateCustomerById(Customer customer , long id) {
		return customerRepository.save(customer);
	}

	public Optional<Customer> findById(long id) {
		return customerRepository.findById(id);
	}

	public Optional<Customer> verifyByCifNumber(long cifNumber, String password) {
		return customerRepository.verifyByCifNumber(cifNumber, password);
	}

	public Optional<Customer> getDataByCifNumber(long cifNumber) {
		return customerRepository.getDataByCifNumber(cifNumber);
	}

	public Optional<Customer> sendMoney(long accountNumber, String password, String ifscCode) {
		return customerRepository.sendMoney(accountNumber, password, ifscCode, 0.0);
	}

	public void updateBalance(long accountNumber, double newBalance) {
		customerRepository.updateBalance(accountNumber, newBalance);
	}
	
	public Optional<Customer> findByAccountNumber(long accountNumber){
		return customerRepository.findByAccountNumber(accountNumber);
	}
	
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}

}
