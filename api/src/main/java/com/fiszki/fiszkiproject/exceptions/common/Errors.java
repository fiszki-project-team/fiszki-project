package com.fiszki.fiszkiproject.exceptions.common;

public class Errors {
	
	public static final String INVALID_PASSWORD = "Given password is invalid";

	public static final String DISPLAY_NAME_TOO_SHORT = "Display name should be at least " 
			+ ValidationBoundries.DISPLAY_NAME_MIN_LENGTH + " characters long";
	
	public static final String PASSWORD_TOO_SHORT = "Password should be at least "
			+ ValidationBoundries.DISPLAY_NAME_MIN_LENGTH + " characters long";

	public static final String PASSWORD_HAS_NO_CAPITAL_LETTERS = "Password must have at least one capital letter";
	
	public static final String PASSWORD_HAS_NO_SPECIAL_CHARS = "Password must have at least one of the special characters";
	
}
