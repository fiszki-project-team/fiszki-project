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
import com.fiszki.fiszkiproject.exceptions.InvalidIdException;
import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
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
		
		return new ResponseEntity<UserBasicInfoDto>(user, HttpStatus.OK);
	}
	
	@PutMapping("/displayName")
	public ResponseEntity<?> changeUserDisplayName(@RequestBody UserNameChangeDto dto) {
		try {	
			userService.changeDisplayName(dto);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (InvalidIdException ie) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (UserValidatorException ue) {
			ErrorMessageDto error = new ErrorMessageDto("UserValidatorException", ue.getMessage());
			
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/password")
	public ResponseEntity<?> changeUserPassword(@RequestBody UserPasswordChangeDto dto) {
		try {	
			userService.changePassword(dto);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (InvalidIdException ie) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (UserValidatorException ue) {
			String type = "UserValidatorException";
			
			ErrorMessageDto error = new ErrorMessageDto(type, ue.getMessage());
			
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

}
