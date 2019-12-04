package com.fiszki.fiszkiproject.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserNameChangeDto {

	private Long id;
	
	private String displayName;
	
	public UserNameChangeDto(Long id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}
	
}
