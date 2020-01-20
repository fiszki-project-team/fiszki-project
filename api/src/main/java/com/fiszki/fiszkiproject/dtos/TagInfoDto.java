package com.fiszki.fiszkiproject.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagInfoDto {

	private Long id;

	private String displayName;

	private int numberOfCards;

	public TagInfoDto(Long id, String displayName, int numberOfCards) {
		this.id = id;
		this.displayName = displayName;
		this.numberOfCards = numberOfCards;
	}

}