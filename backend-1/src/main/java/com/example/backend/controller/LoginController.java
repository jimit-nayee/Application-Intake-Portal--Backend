package com.example.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Exceptions.UserNotApprovedCustomException;
import com.example.backend.Exceptions.UserNotFoundCustomeException;
import com.example.backend.model.User;
import com.example.backend.repo.UserRepo;
import com.example.backend.service.JWTService;
import com.example.backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepo ur;

	@Autowired
	UserService userService;

	@Autowired
	JWTService jwtService;

	@PostMapping("/user")
	public ResponseEntity<String> loginUser(@RequestBody User user)
			throws UserNotFoundCustomeException, UserNotApprovedCustomException {

		if (ur.findByEmail(user.getEmail()).isPresent()) {
//			System.out.println(user);
			User u = ur.findByEmail(user.getEmail()).get();
			if (u.getIs_approved() == 1) {
				try {
					Authentication auth = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
				} catch (Exception e) {
					return new ResponseEntity<String>("Authentication Failed", HttpStatus.UNAUTHORIZED);
				}
				return new ResponseEntity<String>(jwtService.generateToken(user.getEmail()), HttpStatus.OK);
			} else
				throw new UserNotApprovedCustomException();
		} else
			throw new UserNotFoundCustomeException();

	}

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		System.out.println(user);
		String hashPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		return ur.save(user);
	}

	@GetMapping("/getAll")
	public List<User> getAllUser() {
		return ur.findAll();
	}

	@GetMapping("/getAllEmployees")
	public List<User> getEmployeeList() {
		return userService.getAllUsers();
	}

	@PostMapping("/delete")
	public String deleteByUsername(@RequestBody User user) {
		System.out.println(user);
		return userService.deleteByEmail(user.getEmail());
	}

	@PostMapping("/isApprove")
	public String isApprove(@RequestBody User user) {
		userService.updateByEmail(user.getEmail());
		return "status updated";
	}
}
