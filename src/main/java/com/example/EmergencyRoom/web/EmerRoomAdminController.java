package com.example.EmergencyRoom.web;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.EmergencyRoom.domain.User;
import com.example.EmergencyRoom.domain.UserRepository;

//We have a second controller implemented
//It is used to handle the administration of users

@Controller
public class EmerRoomAdminController {
	
	@Autowired
	private UserRepository userRepo;
	
	//We need to hash the passwords to store them, we use BCrypt for that
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	//The methods in this class will be restricted to ADMIN role users only
	//We use the PreAuthorize annotation to handle it, checking the users' roles
	@GetMapping("/userList")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String listUsers(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userList";
	}
	
	@GetMapping("/addUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}
	
	@GetMapping("/updateUser/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateUser(@PathVariable("id") UUID userId, Model model) {
		model.addAttribute("user", userRepo.findById(userId));
		return "updateUser";
	}
	
	
	//Method for updating user's details, taking care of password hashing if updated
	@PostMapping("/saveUpdatedUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveUpdatedUser(@Valid User user, BindingResult bindingResult, @RequestHeader String referer) {
		if (bindingResult.hasErrors() || !(user.getRole().equals("ADMIN") || user.getRole().equals("DOCTOR"))) {
			return "redirect:" + referer;
		}
		String username = user.getUsername();
		if (user.getPasswordHash().isEmpty()) {
			user.setPasswordHash(userRepo.findByUsername(username).getPasswordHash());
		} else {
			user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
		}
		userRepo.save(user);
		return "redirect:/userList";
	}
	
	//Method to create a new user, where we need to hash the password for storage
	@PostMapping("/saveUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveUser(@Valid User user, BindingResult bindingResult, @RequestHeader String referer) {
		if (bindingResult.hasErrors() || !(user.getRole().equals("ADMIN") || user.getRole().equals("DOCTOR")) ||
				userRepo.findByUsername(user.getUsername()) != null) {
			return "redirect:" + referer;
		}
		String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
		user.setPasswordHash(hashedPassword);
		userRepo.save(user);
		return "redirect:/userList";
	}
	
	@GetMapping("/deleteUser/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable("id") UUID userId, Model model) {
		userRepo.deleteById(userId);
		return "redirect:../userList";
	}
}