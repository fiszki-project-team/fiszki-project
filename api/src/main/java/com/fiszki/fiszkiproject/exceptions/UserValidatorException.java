package com.fiszki.fiszkiproject.exceptions;

import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.exceptions.common.BusinessException;

public class UserValidatorException extends BusinessException {

	private static final long serialVersionUID = -714591758139787824L;

	public UserValidatorException(APIErrors error) {
		super(error.toString());
	}

}
