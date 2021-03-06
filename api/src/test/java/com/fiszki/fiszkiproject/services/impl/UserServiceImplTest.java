package com.fiszki.fiszkiproject.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.services.UserService;


@SpringBootTest
@Transactional
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void shouldReturnListOfAllUsers() {
		List<UserBasicInfoDto> users = userService.getAllUsers();
		List<Long> userIds = users.stream().map(u -> u.getId()).collect(Collectors.toList());
		
		assertThat(userIds).containsExactlyInAnyOrder(1L, 2L, 3L);
	}
	
	@Test
	public void shouldReturnUserWithValidId() {
		UserBasicInfoDto user = userService.getUserById(2L);
		
		assertThat(user.getDisplayName()).isEqualTo("user_2");
		assertThat(user.getId()).isEqualTo(2L);
	}
	
	@Test
	public void shouldReturnNullWhenNoUserWithGivenId() {
		UserBasicInfoDto user = userService.getUserById(-1L);
		
		assertThat(user).isNull();
	}
	
	

}
