package org.jsp.bankingmanagementsystemapp.controller;

import java.util.Optional;

import org.jsp.bankingmanagementsystemapp.dto.Admin;
import org.jsp.bankingmanagementsystemapp.dto.ResponseStructure;
import org.jsp.bankingmanagementsystemapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping(value = "/admin")
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}
	
	@PutMapping(value = "/admin")
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(@RequestBody Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@PostMapping(value = "/admin/email")
	public ResponseEntity<ResponseStructure<Optional<Admin>>> verifyByEmail(@RequestParam String email,
			@RequestParam String password) {
		return adminService.verifyByEmail(email, password);
	}
	

//	@GetMapping(value = "/admin")
//	public ResponseEntity<ResponseStructure<List<Admin>>> getAdmin(Model model) {
//
//		return adminService.getAdmin();
//	}
	

    @GetMapping(value = "/admins")
    public String getAdmin(Model model) {
        model.addAttribute("admins", adminService.getAdmin().getBody().getData());
        return "admins";
    }

 
	




}
