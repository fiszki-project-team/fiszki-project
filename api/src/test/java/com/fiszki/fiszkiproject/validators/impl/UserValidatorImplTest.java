package com.fiszki.fiszkiproject.validators.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.exceptions.common.BusinessException;
import com.fiszki.fiszkiproject.validators.UserValidator;

@SpringBootTest
public class UserValidatorImplTest {
	
	@Autowired
	private UserValidator validator;
	
	@Nested
	@DisplayName("validate display name")
	class DisplayNameValidator {
		
		@ParameterizedTest
		@DisplayName("should return true when valid")
		@ValueSource(strings = {"validDisplayName", "1234", "1   R", 
								"!@#$%^", "*******", "???", "@\t$"})
		public void validateValidName(String displayName) throws BusinessException {
			
			boolean result = validator.validateDisplayName(displayName, false);
			
			assertThat(result).isTrue();
		}
		
		@ParameterizedTest
		@DisplayName("should throw exception when name has not enough valid characters")
		@NullAndEmptySource
		@ValueSource(strings = {"   ", "a   ", "    1w", "\t   k1", "  a\n" })
		public void validateWhenNameNotEnoughValidChars(String displayName) throws BusinessException {

			assertThatThrownBy(() -> validator.validateDisplayName(displayName, false))
				.isInstanceOf(BusinessException.class)
				.hasMessage(APIErrors.DISPLAY_NAME_TOO_SHORT.toString());
		}
		
		@Test
		@DisplayName("should throw exception when user exists")
		public void validateWhenUserExists() throws BusinessException {
			String displayName = "validDisplayName";
			
			assertThatThrownBy(() -> validator.validateDisplayName(displayName, true))
				.isInstanceOf(BusinessException.class)
				.hasMessage(APIErrors.DISPLAY_NAME_ALREADY_TAKEN.toString());
		}
	}
	
	@Nested
	@DisplayName("validate password")
	class PasswordValidator {
		
		@ParameterizedTest
		@DisplayName("should return true when valid")
		@ValueSource(strings = {"Testtest!", "thisis@tesT", "12345678A",
								"howDoYouDo?", "P*******", "-BBBBBBB", "B       @"})
		public void validateValidPassword(String password) throws BusinessException {
			
			boolean result = validator.validatePassword(password);
			
			assertThat(result).isTrue();
		}
		
		@ParameterizedTest
		@DisplayName("should throw exception when too short")
		@NullAndEmptySource
		@ValueSource(strings = {"            ", "ana   a", "   s r111w", 
								"\t   k1", "a\t\t\t\t\n" })
		public void validateWhenTooShort(String password) throws BusinessException {

			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(APIErrors.PASSWORD_TOO_SHORT.toString());
		}
		
		@ParameterizedTest
		@DisplayName("shoud throw exception when no capital letter")
		@ValueSource(strings = {"thisis@test", "12345678", "********"})
		public void validateWhenNoCapital(String password) throws BusinessException {
			
			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(APIErrors.PASSWORD_HAS_NO_CAPITAL_LETTERS.toString());
		}
		
		@Test
		@DisplayName("shoud throw exception when no special chars")
		public void validateWhenNoSpecialChars() throws BusinessException {
			String password = "InValiDPassword";
			
			assertThatThrownBy(() -> validator.validatePassword(password))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(APIErrors.PASSWORD_HAS_NO_SPECIAL_CHARS.toString());
		}
	}
	
	@Nested
	@DisplayName("compare passwords")
	class ComparePasswords {
		
		@ParameterizedTest
		@DisplayName("shoud return true when passwords match")
		@ValueSource(strings = {"Testtest!", "thisis@tesT", "12345678A",
				"howDoYouDo?", "P*******", "-BBBBBBB", "B       @"})
		public void validateWhenEquals(String password) throws BusinessException {
			
			boolean result = validator.comparePasswords(password, password);
			
			assertThat(result).isTrue();
		}	
		
		@Test
		@DisplayName("shoud throw exception when passwords are not the same")
		public void validateWhenNotEquals() throws BusinessException {
			String oldPassword = "password";
			String newPassword = "pasSword";
			
			assertThatThrownBy(() -> validator.comparePasswords(oldPassword, newPassword))
				.isInstanceOf(UserValidatorException.class)
				.hasMessage(APIErrors.PASSWORDS_DO_NOT_MATCH.toString());
		}
	}
	
}
