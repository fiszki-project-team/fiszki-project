package com.fiszki.fiszkiproject.mappers;

import org.springframework.stereotype.Component;

import com.fiszki.fiszkiproject.dtos.TagInfoDto;
import com.fiszki.fiszkiproject.persistence.entity.Tag;

@Component
public class TagMapper {

	public TagInfoDto mapToInfo(Tag tag) {
		return new TagInfoDto(tag.getId(), tag.getDisplayName(), new Integer(tag.getCards().size()));
	}

}
