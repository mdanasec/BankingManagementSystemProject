package org.jsp.bankingmanagementsystemapp.exception;

import org.apache.logging.log4j.message.Message;

public class TransactionNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TransactionNotFoundException ( String message) {
		super(message);
	}

}
