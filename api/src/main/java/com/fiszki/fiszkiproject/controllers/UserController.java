package com.fiszki.fiszkiproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiszki.fiszkiproject.dtos.ErrorMessageDto;
import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;
import com.fiszki.fiszkiproject.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public List<UserBasicInfoDto> returnAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> returnSingleUser(@PathVariable Long id) {
		UserBasicInfoDto user = userService.getUserById(id);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<UserBasicInfoDto>(user, HttpStatus.OK);
		}
	}
	
	@PutMapping("/changeDisplayName")
	public ResponseEntity<?> changeUserDisplayName(@RequestBody UserNameChangeDto dto) {
		try {	
			if (userService.changeDisplayName(dto)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ValidatorException e) {
			ErrorMessageDto error = new ErrorMessageDto("UserValidatorException", e.getMessage());
			
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity<?> changeUserPassword(@RequestBody UserPasswordChangeDto dto) {
		try {	
			if (userService.changePassword(dto)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ValidatorException e) {
			String type = "AuthValidatorException";
			
			if (e instanceof UserValidatorException) {
				type = "UserValidatorException";
			}
			
			ErrorMessageDto error = new ErrorMessageDto(type, e.getMessage());
			
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

}
