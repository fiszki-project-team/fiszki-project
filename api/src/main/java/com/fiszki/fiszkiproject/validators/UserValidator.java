package com.fiszki.fiszkiproject.validators;

import com.fiszki.fiszkiproject.exceptions.AuthValidatorException;
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
	
	/**
	 * Checks if given password meets the following conditions:
	 * - is at least 8 characters long
	 * - has at least one capital letter and one of the following: 1234567890!#@&?=_-*$+
	 * 
	 * @param password
	 * @return true when all conditions are fulfilled
	 * @throws UserValidatorException when any of the checked conditions is not fulfilled
	 */
	boolean validatePassword(String password) throws UserValidatorException;
	
	/**
	 * Checks if given passwords match.
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return true if match
	 * @throws AuthValidatorException when do not match
	 */
	boolean comparePasswords(String oldPassword, String newPassword) throws AuthValidatorException;

}
