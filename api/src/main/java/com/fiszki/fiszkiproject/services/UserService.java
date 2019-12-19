package com.fiszki.fiszkiproject.services;

import java.util.List;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;

public interface UserService {
	
	List<UserBasicInfoDto> getAllUsers();
	
	UserBasicInfoDto getUserById(Long id);
	
}
