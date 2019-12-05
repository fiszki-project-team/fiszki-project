package com.fiszki.fiszkiproject.exceptions;

import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;

public class AuthValidatorException extends ValidatorException {

	private static final long serialVersionUID = -9183962468246321483L;

	public AuthValidatorException(String message) {
		super(message);
	}

}
