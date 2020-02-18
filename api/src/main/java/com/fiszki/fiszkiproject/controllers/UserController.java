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

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
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
	
	@PutMapping("/{id}/display-name")
	public ResponseEntity<?> changeUserDisplayName(@PathVariable Long id, @RequestBody UserNameChangeDto dto) {
		dto.setId(id);
		userService.changeDisplayName(dto);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}/password")
	public ResponseEntity<?> changeUserPassword(@PathVariable Long id, @RequestBody UserPasswordChangeDto dto) {
		dto.setId(id);
		userService.changePassword(dto);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
