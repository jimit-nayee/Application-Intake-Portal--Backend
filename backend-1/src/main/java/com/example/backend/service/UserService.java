package com.example.backend.service;

import java.util.List;
import com.example.backend.model.User;

public interface UserService {
	public User register(User user);
	public String login(User user);
	List<User> getAllUsers();
	String deleteByEmail(String email);
	
}
