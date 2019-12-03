package com.fiszki.fiszkiproject.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserBasicInfoDto {
	
	private Long id;
	
	private String email;
	
	private String displayName;
	
	public UserBasicInfoDto(Long id, String email, String displayName) {
		this.id = id;
		this.email = email;
		this.displayName = displayName;
	}

}
