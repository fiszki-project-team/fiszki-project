package com.fiszki.fiszkiproject.validators.impl;

import org.springframework.stereotype.Component;

import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.exceptions.common.ValidationBoundries;
import com.fiszki.fiszkiproject.validators.UserValidator;

@Component
public class UserValidatorImpl implements UserValidator {

	@Override
	public boolean validateDisplayName(String displayName, boolean userExists) throws UserValidatorException {

		if (displayName == null 
				|| displayName.trim().length() < ValidationBoundries.DISPLAY_NAME_MIN_LENGTH) {
			throw new UserValidatorException(APIErrors.DISPLAY_NAME_TOO_SHORT);
		}
		
		if (userExists) {
			throw new UserValidatorException(APIErrors.DISPLAY_NAME_ALREADY_TAKEN);
		}
		
		return true;
	}

	@Override
	public boolean validatePassword(String password) throws UserValidatorException {
		
		if (password == null 
				|| password.trim().length() < ValidationBoundries.PASSWORD_MIN_LENGTH) {
			throw new UserValidatorException(APIErrors.PASSWORD_TOO_SHORT);
		}
		
		if (password.toLowerCase().equals(password)) {
			throw new UserValidatorException(APIErrors.PASSWORD_HAS_NO_CAPITAL_LETTERS);
		}
		
		if (hasNoSpecialCharacters(password)) {
			throw new UserValidatorException(APIErrors.PASSWORD_HAS_NO_SPECIAL_CHARS);
		}
		
		return true;
	}

	private boolean hasNoSpecialCharacters(String password) {
		int size = password.length();
		for (int i = 0; i < size; i++) {
			if (arrayContains(ValidationBoundries.PASSWORD_SPECIAL_CHARS, password.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	private boolean arrayContains(char[] charArr, char charToCheck) {
		int size = charArr.length;
		for (int i = 0; i < size; i++) {
			if (charArr[i] == charToCheck) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean comparePasswords(String oldPassword, String newPassword) throws UserValidatorException {
		if (!oldPassword.equals(newPassword)) {
			throw new UserValidatorException(APIErrors.PASSWORDS_DO_NOT_MATCH);
		}
		
		return true;
	}

}
