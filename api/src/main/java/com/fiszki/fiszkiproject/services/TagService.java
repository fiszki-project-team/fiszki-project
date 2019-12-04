package com.fiszki.fiszkiproject.services;

import java.util.List;

import com.fiszki.fiszkiproject.dtos.TagInfoDto;

public interface TagService {
	
	List<TagInfoDto> getTagsByUserId(Long userId);
	
}
