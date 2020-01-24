package com.fiszki.fiszkiproject.exceptions;

import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.exceptions.common.BusinessException;

/**
 * 
 * This exception is thrown when there are validation errors with user data
 * other than user ID.
 *
 */
public class UserValidatorException extends BusinessException {

	private static final long serialVersionUID = -714591758139787824L;

	public UserValidatorException(APIErrors error) {
		super(error.toString());
	}

}
