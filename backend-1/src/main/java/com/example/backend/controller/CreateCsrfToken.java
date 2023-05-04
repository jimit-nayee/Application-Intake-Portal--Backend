package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreateCsrfToken {
	
	@GetMapping("/csrf")
	public void generate() {
		System.out.println("csrf token get request made...");
	}

}
