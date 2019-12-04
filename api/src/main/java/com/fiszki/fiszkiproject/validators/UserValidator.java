package com.fiszki.fiszkiproject.validators;

import com.fiszki.fiszkiproject.exceptions.UserValidatorException;

public interface UserValidator {
	
	/**
	 * Checks if given display name meets the following conditions:
	 * - is at least 3 characters long
	 * 
	 * @param displayName
	 * @return true when all conditions are fulfilled
	 * @throws UserValidatorException when any of the checked conditions is not fulfilled
	 */
	boolean validateDisplayName(String displayName) throws UserValidatorException;

}
