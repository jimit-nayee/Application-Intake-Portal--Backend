package com.example.backend.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreateCsrfToken {
	
	@GetMapping("/csrf")
	public CsrfToken generate(CsrfToken token) {
		return token;
	}

}
