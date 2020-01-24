package com.fiszki.fiszkiproject.validators;

import com.fiszki.fiszkiproject.exceptions.UserValidatorException;

public interface UserValidator {
	
	/**
	 * Checks if given display name meets the following conditions:
	 * - is at least 3 characters long
	 * - is unique
	 * 
	 * @param displayName
	 * @param userExists - value that determines if user with given display name exists in db
	 * @return true when all conditions are fulfilled
	 * @throws UserValidatorException when any of the checked conditions is not fulfilled
	 */
	boolean validateDisplayName(String displayName, boolean userExists) throws UserValidatorException;
	
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
	 * @throws UserValidatorException when do not match
	 */
	boolean comparePasswords(String oldPassword, String newPassword) throws UserValidatorException;

}
