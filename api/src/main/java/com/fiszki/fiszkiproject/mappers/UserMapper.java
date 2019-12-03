package com.fiszki.fiszkiproject.mappers;

import org.springframework.stereotype.Component;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.persistence.entity.User;

@Component
public class UserMapper {
	
	public UserBasicInfoDto mapToBasicInfo(User user) {
		return new UserBasicInfoDto(
				user.getId(), 
				user.getEmail(), 
				user.getDisplayName()
				);
	}

}
