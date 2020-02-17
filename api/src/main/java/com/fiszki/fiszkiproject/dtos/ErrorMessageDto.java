package com.fiszki.fiszkiproject.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ErrorMessageDto {
	
	private LocalDateTime timeStamp;

	private String type;
	
	private String message;
	
	public ErrorMessageDto(String type, String message) {
		this.timeStamp = LocalDateTime.now();
		this.type = type;
		this.message = message;
	}
	
}
