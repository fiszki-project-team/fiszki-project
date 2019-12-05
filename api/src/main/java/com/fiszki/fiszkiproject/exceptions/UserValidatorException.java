package com.fiszki.fiszkiproject.exceptions;

import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;

public class UserValidatorException extends ValidatorException {

	private static final long serialVersionUID = -714591758139787824L;

	public UserValidatorException(Errors error) {
		super(error.toString());
	}

}
