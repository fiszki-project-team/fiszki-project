package com.fiszki.fiszkiproject.validators.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;
import com.fiszki.fiszkiproject.validators.UserValidator;

@SpringBootTest
public class UserValidatorImplTest {
	
	@Autowired
	private UserValidator validator;
	
	@Nested
	@DisplayName("validate display name")
	class DisplayNameValidator {
		
		@Test
		@DisplayName("should return true when valid")
		public void shouldReturnTrueWhenIsValid() throws ValidatorException {
			String displayName = "validDisplayName";
			
			boolean result = validator.validateDisplayName(displayName);
			
			assertThat(result).isTrue();
		}
		
		@Test
		@DisplayName("should throw exception when null")
		public void shouldThrowExceptionWhenNameIsNull() throws ValidatorException {
			String displayName = null;
			
			assertThatThrownBy(() -> validator.validateDisplayName(displayName))
				.isInstanceOf(ValidatorException.class)
				.hasMessage(Errors.DISPLAY_NAME_TOO_SHORT);
		}
		
		@Test
		@DisplayName("should throw exception when name has not enough valid characters")
		public void validateWhenNotEnoughValidCharacters() throws ValidatorException {
			String displayName = "   ab   ";
			
			assertThatThrownBy(() -> validator.validateDisplayName(displayName))
				.isInstanceOf(ValidatorException.class)
				.hasMessage(Errors.DISPLAY_NAME_TOO_SHORT);
		}
	}
	
	@Nested
	@DisplayName("validate password")
	class PasswordValidator {
		
		@ParameterizedTest
		@DisplayName("should return true when valid")
		@ValueSource(strings = {"Testtest!", "thisis@tesT", "12345678A", "howDoYouDo?", "P*******", "-BBBBBBB"})
		public void validateValidPassword(String password) throws ValidatorException {
			boolean result = validator.validatePassword(password);
			
			assertThat(result).isTrue();
		}
		
		@Test
		@DisplayName("should throw exception when null")
		public void validateNull() throws ValidatorException {
			String password = null;
			
			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(Errors.PASSWORD_TOO_SHORT);
		}
		
		@Test
		@DisplayName("should throw exception when too short")
		public void validateWhenTooShort() throws ValidatorException {
			String password = "TooShor  ";
			
			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(Errors.PASSWORD_TOO_SHORT);
		}
		
		@ParameterizedTest
		@DisplayName("shoud throw exception when no capital letter")
		@ValueSource(strings = {"thisis@test", "12345678", "********"})
		public void validateWhenNoCapital(String password) throws ValidatorException {
			
			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(Errors.PASSWORD_HAS_NO_CAPITAL_LETTERS);
		}
		
		@Test
		@DisplayName("shoud throw exception when no special chars")
		public void validateWhenNoSpecialChars() throws ValidatorException {
			String password = "InValiDPassword";
			
			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(Errors.PASSWORD_HAS_NO_SPECIAL_CHARS);
		}
	}
	
}
