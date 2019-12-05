package com.fiszki.fiszkiproject.exceptions.common;

public class ValidationBoundries {
	
	public static final int DISPLAY_NAME_MIN_LENGTH = 3;
	public static final int PASSWORD_MIN_LENGTH = 8;
	
	// if you change the values, please update the docs in UserValidator.changePassword method
	public static final char[] PASSWORD_SPECIAL_CHARS 
		= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'!', '#', '@', '&', '?', '=', '_', '-', '*', '$', '+'};

}
