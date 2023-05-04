package com.example.backend.Exceptions;

public class UserNotFoundCustomeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundCustomeException() {
		
		super("User name Not Found!!");
	}

}
