package org.jsp.bankingmanagementsystemapp.repository;

import java.util.List;

import org.jsp.bankingmanagementsystemapp.dto.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	@Query("SELECT t FROM Transaction t WHERE t.customer.cifNumber = :cifNumber")
	List<Transaction> findByCifNumber(@Param("cifNumber") Long cifNumber); //Make sure to include @Param("cifNumber")
	//	to link the method parameter cifNumber with the query parameter. Without this, Spring won't be 
	//	able to map the method parameter to the query parameter.

}
