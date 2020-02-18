package com.fiszki.fiszkiproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fiszki.fiszkiproject.dtos.ErrorMessageDto;
import com.fiszki.fiszkiproject.exceptions.InvalidIdException;
import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.APIErrorsTypes;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleDefaultExceptions(Exception ex, WebRequest request) {
		
		ErrorMessageDto response = new ErrorMessageDto(APIErrorsTypes.UNIDENTIFIED_ERROR.toString(), ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidIdException.class)
	public final ResponseEntity<Object> handleUNFExceptions(InvalidIdException ex, WebRequest request) {
		
		ErrorMessageDto response = new ErrorMessageDto(APIErrorsTypes.RESOURCE_NOT_FOUND.toString(), ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}	
	
	@ExceptionHandler(UserValidatorException.class)
	public final ResponseEntity<Object> handleValidationExceptions(UserValidatorException ex, WebRequest request) {
		
		ErrorMessageDto response = new ErrorMessageDto(APIErrorsTypes.VALIDATION_ERROR.toString(), ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
