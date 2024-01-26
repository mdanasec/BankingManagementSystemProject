package org.jsp.bankingmanagementsystemapp.repository;

import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c WHERE c.cifNumber=?1 AND c.password=?2")
	public Optional<Customer> verifyByCifNumber(long cifNumber, String password);

	@Query("SELECT c FROM Customer c WHERE c.cifNumber=?1 ")
	public Optional<Customer> getDataByCifNumber(long cifNumber);

	@Query("SELECT c FROM Customer c WHERE c.accountNumber=?1 AND c.password=?2 AND c.ifscCode=?3 AND c.balance >= ?4")
	Optional<Customer> sendMoney(long accountNumber, String password, String ifscCode, double balance);

	@Modifying
	@Query("UPDATE Customer c SET c.balance = ?2 WHERE c.accountNumber = ?1")
	void updateBalance(long accountNumber, double newBalance);
	
	@Query("SELECT c FROM Customer c WHERE c.accountNumber=?1")
	public Optional<Customer> findByAccountNumber(long accountNumber);
	
	
	
	
	
	
}



