package com.example.backend.Handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.backend.Exceptions.UserNotFoundCustomeException;

import org.springframework.web.bind.annotation.ExceptionHandler;



@RestControllerAdvice
public class MyExceptionHandler {
	
	
	@ExceptionHandler(UserNotFoundCustomeException.class)
	public ResponseEntity<?> Mymessage(UserNotFoundCustomeException c){
		return new ResponseEntity<>(c.getMessage(),HttpStatus.NOT_FOUND);
	}

}
