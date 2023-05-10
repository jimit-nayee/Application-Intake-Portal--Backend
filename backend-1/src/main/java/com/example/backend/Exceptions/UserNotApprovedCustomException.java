package com.example.backend.Exceptions;

public class UserNotApprovedCustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNotApprovedCustomException()
	{	
		super("User is not Approved yet !!!");
	}

}
