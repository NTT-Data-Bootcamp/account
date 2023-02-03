package com.bootcamp.java.account.service.exception;

public class InvalidClientProfileException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidClientProfileException() {
		super("This type of client profile is invalid");
	}
}
