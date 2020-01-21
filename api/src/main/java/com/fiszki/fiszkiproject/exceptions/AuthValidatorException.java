package com.fiszki.fiszkiproject.exceptions;

import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.exceptions.common.BusinessException;

public class AuthValidatorException extends BusinessException {

	private static final long serialVersionUID = -9183962468246321483L;

	public AuthValidatorException(APIErrors error) {
		super(error.toString());
	}

}
