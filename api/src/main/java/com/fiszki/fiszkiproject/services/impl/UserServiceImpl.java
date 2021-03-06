package com.fiszki.fiszkiproject.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.mappers.UserMapper;
import com.fiszki.fiszkiproject.persistence.entity.User;
import com.fiszki.fiszkiproject.repositories.UserRepository;
import com.fiszki.fiszkiproject.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper mapper;
	private UserRepository repository;
	
	@Autowired
	public UserServiceImpl(UserMapper mapper, UserRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public List<UserBasicInfoDto> getAllUsers() {
		return repository.findAll().stream()
				.map(u -> mapper.mapToBasicInfo(u))
				.collect(Collectors.toList());
	}

	@Override
	public UserBasicInfoDto getUserById(Long id) {
		Optional<User> user = repository.findById(id);
		
		if (user.isPresent()) {
			return mapper.mapToBasicInfo(user.get());
		} else {
			return null;
		}
	}

}
