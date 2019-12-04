package com.fiszki.fiszkiproject.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;
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
	
	@Test
	public void shouldSuccessfullyChangeDisplayName() throws ValidatorException {
		Long userId = 3L;
		String newDisplayName = "validName";
		UserNameChangeDto dto = new UserNameChangeDto(userId, newDisplayName);
		UserBasicInfoDto beforeChange = userService.getUserById(userId);
		
		boolean result = userService.changeDisplayName(dto);
		UserBasicInfoDto afterChange = userService.getUserById(userId);
		
		assertThat(result).isTrue();
		assertThat(afterChange.getDisplayName()).isEqualTo(newDisplayName);
		assertThat(afterChange.getDisplayName()).isNotEqualTo(beforeChange.getDisplayName());
	}
	
	@Test
	public void shouldReturnFalseWhenUserIdIsNotValid() throws ValidatorException {
		Long userId = -1L;
		String newDisplayName = "validName";
		UserNameChangeDto dto = new UserNameChangeDto(userId, newDisplayName);
		
		boolean result = userService.changeDisplayName(dto);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void shouldThrowExceptionWhenNewNameIsInvalid() throws ValidatorException {
		Long userId = 1L;
		String newDisplayName = "r    ";
		UserNameChangeDto dto = new UserNameChangeDto(userId, newDisplayName);
		
		assertThatThrownBy(() -> userService.changeDisplayName(dto))
			.isInstanceOf(ValidatorException.class)
			.hasMessage(Errors.DISPLAY_NAME_TOO_SHORT);
	}
	

}
