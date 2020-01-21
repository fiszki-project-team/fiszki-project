package com.fiszki.fiszkiproject.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiszki.fiszkiproject.dtos.TagInfoDto;
import com.fiszki.fiszkiproject.mappers.TagMapper;
import com.fiszki.fiszkiproject.repositories.TagRepository;
import com.fiszki.fiszkiproject.repositories.UserRepository;
import com.fiszki.fiszkiproject.services.TagService;

@Service
public class TagServiceImpl implements TagService {

	private TagMapper mapper;
	private TagRepository tagRepository;
	private UserRepository userRepository;

	@Autowired
	public TagServiceImpl(TagMapper mapper, TagRepository tagRepository, UserRepository userRepository) {
		this.mapper = mapper;
		this.tagRepository = tagRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<TagInfoDto> getTagsByUserId(Long id) {
		userRepository.findByIdWithIdValidation(id);
		
		return tagRepository.findTagsByUserId(id).stream().map(t -> mapper.mapToInfo(t)).collect(Collectors.toList());
	}

}
