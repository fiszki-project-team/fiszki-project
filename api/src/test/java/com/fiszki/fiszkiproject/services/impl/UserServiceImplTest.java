package com.fiszki.fiszkiproject.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
import com.fiszki.fiszkiproject.exceptions.AuthValidatorException;
import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;
import com.fiszki.fiszkiproject.persistence.entity.User;
import com.fiszki.fiszkiproject.repositories.UserRepository;
import com.fiszki.fiszkiproject.services.UserService;
import com.fiszki.fiszkiproject.validators.UserValidator;


@SpringBootTest
@Transactional
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@Nested
	@DisplayName("get user")
	class GetUser {
		
		@Test
		@DisplayName("should return list of all users")
		public void shouldReturnListOfAllUsers() {
			List<UserBasicInfoDto> users = userService.getAllUsers();
			List<Long> userIds = users.stream().map(u -> u.getId()).collect(Collectors.toList());
			
			assertThat(userIds).containsExactlyInAnyOrder(1L, 2L, 3L);
		}
		
		@Test
		@DisplayName("should return user with valid id")
		public void shouldReturnUserWithValidId() {
			UserBasicInfoDto user = userService.getUserById(2L);
			
			assertThat(user.getDisplayName()).isEqualTo("user_2");
			assertThat(user.getId()).isEqualTo(2L);
		}
		
		@Test
		@DisplayName("should return null with invalid id")
		public void shouldReturnNullWhenNoUserWithGivenId() {
			UserBasicInfoDto user = userService.getUserById(-1L);
			
			assertThat(user).isNull();
		}
	}
	
	@Nested
	@DisplayName("change display name")
	class ChangeDisplayName {
		
		@Test
		@DisplayName("should return true for valid name for valid user")
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
		@DisplayName("should return false for valid name and invalid user")
		public void shouldReturnFalseWhenUserIdIsNotValid() throws ValidatorException {
			Long userId = -1L;
			String newDisplayName = "validName";
			UserNameChangeDto dto = new UserNameChangeDto(userId, newDisplayName);
			
			boolean result = userService.changeDisplayName(dto);
			
			assertThat(result).isFalse();
		}
		
		@Test
		@DisplayName("should throw exception when invalid name")
		public void shouldThrowExceptionWhenNewNameIsInvalid() throws ValidatorException {
			Long userId = 1L;
			String newDisplayName = "r    ";
			UserNameChangeDto dto = new UserNameChangeDto(userId, newDisplayName);
			
			assertThatThrownBy(() -> userService.changeDisplayName(dto))
				.isInstanceOf(ValidatorException.class)
				.hasMessage(Errors.DISPLAY_NAME_TOO_SHORT.toString());
		}
		
		@Test
		@DisplayName("should throw exception when name already taken")
		public void shouldThrowExceptionWhenNewNameAlreadyTaken() throws ValidatorException {
			Long userId = 2L;
			String newDisplayName = "user_1";
			UserNameChangeDto dto = new UserNameChangeDto(userId, newDisplayName);
			
			assertThatThrownBy(() -> userService.changeDisplayName(dto))
				.isInstanceOf(ValidatorException.class)
				.hasMessage(Errors.DISPLAY_NAME_ALREADY_TAKEN.toString());
		}
	}
	
	@Nested
	@DisplayName("change password")
	class ChangePassword {
		
		UserPasswordChangeDto dto;
		
		@Mock
		UserValidator validator;
		
		@Mock
		UserRepository repository;
		
		@InjectMocks
		UserServiceImpl mockedService;
		
		@BeforeEach
		void setUp() {
			dto = new UserPasswordChangeDto(1L, "test", "Test1234");
		}
		
		@Test
		@DisplayName("should return true when valid password and user id")
		public void changeValidPassword() throws ValidatorException {
	
			boolean result = userService.changePassword(dto);
			
			assertThat(result).isTrue();
		}
		
		@Test
		@DisplayName("should return false when valid password and invalid user id")
		public void changeInvalidUserId() throws ValidatorException {
			dto.setId(-1L);
			
			boolean result = userService.changePassword(dto);
			
			assertThat(result).isFalse();
		}
		
		@Test
		@DisplayName("should throw exception when invalid new password")
		public void changeInvalidNewPassword() throws ValidatorException {
			dto.setNewPassword("12345678");
			
			assertThatThrownBy(() -> userService.changePassword(dto))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(Errors.PASSWORD_HAS_NO_CAPITAL_LETTERS.toString());
		}
		
		@Test
		@DisplayName("should throw exception when invalid old password")
		public void changeInvalidOldPassword() throws ValidatorException {
			when(repository.findById(any(Long.class))).thenReturn(Optional.of(new User()));
			when(validator.comparePasswords(any(), any(String.class)))
				.thenThrow(new AuthValidatorException(Errors.INVALID_PASSWORD));
			
			assertThatThrownBy(() -> mockedService.changePassword(dto))
				.isInstanceOf(AuthValidatorException.class)
				.hasMessage(Errors.INVALID_PASSWORD.toString());
		}
		
	}

}
