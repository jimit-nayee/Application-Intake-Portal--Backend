package com.example.backend.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Transient;

@Document
@Component
public class User {

	@Id
	private String id;
	private String email;
	private String role;
	private String mono;
	private String password;

	public User() {
		super();
	}

	public User(String email, String role, String mono, String password) {
		super();
		this.email = email;
		this.role = role;
		this.mono = mono;
		this.password = password;
	}

	public User(String id, String email, String role, String mono, String password) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.mono = mono;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMono() {
		return mono;
	}

	public void setMono(String mono) {
		this.mono = mono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", email=" + email + ", role=" + role + ", mono=" + mono + ", password="
				+ password + "]";
	}

}
