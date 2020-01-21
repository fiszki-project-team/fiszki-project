package com.fiszki.fiszkiproject.exceptions;

import com.fiszki.fiszkiproject.exceptions.common.BusinessException;

/**
 * This exception is thrown when given id is invalid and does not occur in Database.
 *
 */
public class InvalidIdException extends BusinessException {

	private static final long serialVersionUID = -3507449302651893750L;

	public InvalidIdException(String message) {
		super(message);
	}

}
