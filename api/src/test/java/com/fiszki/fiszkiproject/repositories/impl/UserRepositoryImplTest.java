package com.fiszki.fiszkiproject.repositories.impl;

import static org.assertj.core.api.Assertions.assertThatObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.exceptions.InvalidIdException;
import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.persistence.entity.User;
import com.fiszki.fiszkiproject.repositories.UserRepository;

@SpringBootTest
@Transactional
class UserRepositoryImplTest {

	@Autowired
	private UserRepository userRepository;

	@Nested
	@DisplayName("validate User by id in DataBase")
	class UserValidation {

		@ParameterizedTest
		@DisplayName("should throw exception when user does not exist in DataBase (under given Id)")
		@NullSource
		@ValueSource(longs = { 1500L, -300L, 0L })
		public void validateWhenUserIsNotInDataBase(Long Id) {

			assertThrows(InvalidIdException.class, () -> userRepository.findByIdWithIdValidation(Id),
					APIErrors.USER_NOT_IN_DATABASE.name());
		}
		
		@ParameterizedTest
		@DisplayName("should return User object when user exists in DataBase")
		@ValueSource(longs = {1L, 2L, 3L})
		public void returnUserWhenIsInDataBase(Long Id) {
			User user = userRepository.findByIdWithIdValidation(Id);
			
			assertThatObject(user).isNotNull();
			assertEquals(Id, user.getId());
		}
	}

}
