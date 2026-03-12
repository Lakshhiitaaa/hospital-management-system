package com.hms.hospital.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
	public EmailAlreadyExistsException(String msg) {
		super(msg);
	}
}
