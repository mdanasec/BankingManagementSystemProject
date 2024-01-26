package org.jsp.bankingmanagementsystemapp.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dto.Admin;
import org.jsp.bankingmanagementsystemapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {

	@Autowired
	private AdminRepository adminRepository;

	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}
	
	public Optional<Admin> findById(int adminId) {
		return adminRepository.findById(adminId);
	}

	public Optional<Admin> verifyByEmail(String email, String password) {
		return adminRepository.verifyByEmail(email, password);
	}
	
	public List<Admin> getAdmin(){
		return adminRepository.findAll();
	}
}
