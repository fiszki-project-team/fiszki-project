package com.fiszki.fiszkiproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserBasicInfoDto {
	
	private Long id;
	
	private String email;
	
	private String displayName;
	
	public UserBasicInfoDto() {}
	
	public UserBasicInfoDto(Long id, String email, String displayName) {
		this.id = id;
		this.email = email;
		this.displayName = displayName;
	}

}
