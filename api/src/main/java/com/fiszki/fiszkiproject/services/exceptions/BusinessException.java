package com.fiszki.fiszkiproject.services.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -918302322810716265L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
