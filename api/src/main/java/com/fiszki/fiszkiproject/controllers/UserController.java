package com.fiszki.fiszkiproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
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
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<UserBasicInfoDto>(user, HttpStatus.OK);
		}
	}

}
