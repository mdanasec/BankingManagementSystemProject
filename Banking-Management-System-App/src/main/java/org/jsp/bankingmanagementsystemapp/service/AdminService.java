package org.jsp.bankingmanagementsystemapp.service;

import java.util.List;
import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dao.AdminDao;
import org.jsp.bankingmanagementsystemapp.dto.Admin;
import org.jsp.bankingmanagementsystemapp.dto.ResponseStructure;
import org.jsp.bankingmanagementsystemapp.exception.AdminNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(Admin admin) {
		ResponseStructure<Admin> structure = new ResponseStructure<>();
		structure.setMessage("admin saved sucessfully");
		structure.setData(adminDao.saveAdmin(admin));
		structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(Admin admin) {
		ResponseStructure<Admin> structure = new ResponseStructure<>();
		structure.setMessage("admin updated sucessfully");
		structure.setData(adminDao.saveAdmin(admin));
		structure.setStatusCode(HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ResponseStructure<Admin>>(structure, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<Optional<Admin>>> verifyByEmail(String email, String password) {
		ResponseStructure<Optional<Admin>> structure = new ResponseStructure<>();
		Optional<Admin> recAdmin = adminDao.verifyByEmail(email, password);

		if (recAdmin.isPresent()) {
			structure.setMessage("login successfully ");
			structure.setData(recAdmin);
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Optional<Admin>>>(structure, HttpStatus.OK);
		}
		throw new AdminNotFoundException("you have entred wrong email or password !!");
	}

	public ResponseEntity<ResponseStructure<List<Admin>>> getAdmin() {
		ResponseStructure<List<Admin>> structure = new ResponseStructure<>();
		List<Admin> recAdmins= adminDao.getAdmin();
		if(recAdmins.size()>0) {
			structure.setMessage("admin found ");
			structure.setData(recAdmins);
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Admin>>> (structure, HttpStatus.OK);
		}
		throw new AdminNotFoundException("admin not found");
	}

}
