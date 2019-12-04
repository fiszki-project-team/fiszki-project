package com.fiszki.fiszkiproject.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ErrorMessageDto {

	private String type;
	
	private String message;
	
	public ErrorMessageDto(String type, String message) {
		this.type = type;
		this.message = message;
	}
	
}
