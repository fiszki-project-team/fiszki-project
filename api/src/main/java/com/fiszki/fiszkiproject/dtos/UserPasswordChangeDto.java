package com.fiszki.fiszkiproject.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserPasswordChangeDto {

	private Long id;
	
	private String oldPassword;
	
	private String newPassword;

	public UserPasswordChangeDto(Long id, String oldPassword, String newPassword) {
		this.id = id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
}
