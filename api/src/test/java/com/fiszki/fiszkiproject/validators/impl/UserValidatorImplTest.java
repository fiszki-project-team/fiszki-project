package com.fiszki.fiszkiproject.validators.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.exceptions.common.ValidatorException;
import com.fiszki.fiszkiproject.validators.UserValidator;

@SpringBootTest
public class UserValidatorImplTest {
	
	@Autowired
	private UserValidator validator;
	
	@Test
	public void shouldReturnTrueWhenIsValid() throws ValidatorException {
		String displayName = "validDisplayName";
		
		boolean result = validator.validateDisplayName(displayName);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void shouldThrowExceptionWhenNameIsNull() throws ValidatorException {
		String displayName = null;
		
		assertThatThrownBy(() -> validator.validateDisplayName(displayName))
			.isInstanceOf(ValidatorException.class)
			.hasMessage(Errors.DISPLAY_NAME_TOO_SHORT);
	}
	
	@Test
	public void shouldThrowExceptionWhenNameHasNotEnoughValidCharacters() throws ValidatorException {
		String displayName = "   ab   ";
		
		assertThatThrownBy(() -> validator.validateDisplayName(displayName))
			.isInstanceOf(ValidatorException.class)
			.hasMessage(Errors.DISPLAY_NAME_TOO_SHORT);
	}

}
