package com.fiszki.fiszkiproject.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
import com.fiszki.fiszkiproject.exceptions.common.BusinessException;
import com.fiszki.fiszkiproject.mappers.UserMapper;
import com.fiszki.fiszkiproject.persistence.entity.User;
import com.fiszki.fiszkiproject.repositories.UserRepository;
import com.fiszki.fiszkiproject.services.UserService;
import com.fiszki.fiszkiproject.validators.UserValidator;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper mapper;
	private UserRepository repository;
	private UserValidator validator;
	
	@Autowired
	public UserServiceImpl(UserMapper mapper, 
			UserRepository repository, UserValidator validator) {
		this.mapper = mapper;
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public List<UserBasicInfoDto> getAllUsers() {
		return repository.findAll().stream()
				.map(u -> mapper.mapToBasicInfo(u))
				.collect(Collectors.toList());
	}

	@Override
	public UserBasicInfoDto getUserById(Long id) {
		User entity = repository.findByIdWithIdValidation(id);
		
		return mapper.mapToBasicInfo(entity);
	}

	@Override
	public boolean changeDisplayName(UserNameChangeDto dto) throws BusinessException {
		User entity = repository.findByIdWithIdValidation(dto.getId());
		
		User withGivenDisplayName = repository.findByDisplayName(dto.getDisplayName());
		validator.validateDisplayName(dto.getDisplayName(), withGivenDisplayName != null);
		
		entity.setDisplayName(dto.getDisplayName());
		repository.save(entity);		
			
		return true;
	}

	@Override
	public boolean changePassword(UserPasswordChangeDto dto) throws BusinessException {
		User entity = repository.findByIdWithIdValidation(dto.getId());
		
		validator.validatePassword(dto.getNewPassword());
		validator.comparePasswords(entity.getPassword(), encodePassword(dto.getOldPassword()));
		
		entity.setPassword(encodePassword(dto.getNewPassword()));
		repository.save(entity);
		
		return true;
	}
	
	private String encodePassword(String password) {
		// TODO - to be implemented once auth section is up and running
		return password;
	}

}
