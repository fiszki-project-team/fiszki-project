package com.fiszki.fiszkiproject.validators.impl;

import org.springframework.stereotype.Component;

import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.exceptions.common.ValidationBoundries;
import com.fiszki.fiszkiproject.validators.UserValidator;

@Component
public class UserValidatorImpl implements UserValidator {

	@Override
	public boolean validateDisplayName(String displayName) throws UserValidatorException {

		if (displayName == null 
			|| displayName.trim().length() < ValidationBoundries.DISPLAY_NAME_MIN_LENGTH) {
			throw new UserValidatorException(Errors.DISPLAY_NAME_TOO_SHORT);
		}
		
		return true;
	}

}
