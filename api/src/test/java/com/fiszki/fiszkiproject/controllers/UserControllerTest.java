package com.fiszki.fiszkiproject.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
import com.fiszki.fiszkiproject.exceptions.common.APIErrors;
import com.fiszki.fiszkiproject.exceptions.common.APIErrorsTypes;
import com.fiszki.fiszkiproject.services.UserService;

@SpringBootTest
@Transactional
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private UserService userService;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(new UserController(userService))
				.setControllerAdvice(new CustomExceptionHandler())
				.build();
	}

	@Nested
	@DisplayName("get single user")
	class UsersIdEndpoint {

		@Test
		@DisplayName("should return 404 when user does not exist")
		public void getInValidUser() throws Exception {
			final ResultActions result = mockMvc.perform(get("/api/users/100"));

			result.andExpect(status().isNotFound())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.RESOURCE_NOT_FOUND.toString())))
					.andExpect(jsonPath("$.message", is(APIErrors.USER_NOT_IN_DATABASE.toString())));
		}

		@Test
		@DisplayName("should return return correct json data when user exists")
		public void getValidUser() throws Exception {

			final ResultActions result = mockMvc.perform(get("/api/users/2"));

			result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id", is(2))).andExpect(jsonPath("$.displayName", is("user_2")))
					.andExpect(jsonPath("$.email", is("user2@test.com")));
		}

	}

	@Nested
	@DisplayName("change displayName")
	class ChangeNameEndpoint {

		private UserNameChangeDto dto;
		private String jsonContent;

		@Test
		@DisplayName("should return no content when success")
		public void changeWhenWasOk() throws Exception {
			dto = new UserNameChangeDto(1L, "validName");
			jsonContent = new ObjectMapper().writeValueAsString(dto);

			final ResultActions result = mockMvc.perform(put("/api/users/1/display-name")
					.contentType(MediaType.APPLICATION_JSON).content(jsonContent));

			result.andExpect(status().isNoContent());
		}
		
		@Test
		@DisplayName("should return 404 when user does not exist")
		public void changeWithInvalidUserId() throws Exception {
			dto = new UserNameChangeDto(-1L, "validName");
			jsonContent = new ObjectMapper().writeValueAsString(dto);
			
			final ResultActions result = mockMvc.perform(put("/api/users/1000/display-name")
					.contentType(MediaType.APPLICATION_JSON).content(jsonContent));

			result.andExpect(status().isNotFound())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.RESOURCE_NOT_FOUND.toString())))
					.andExpect(jsonPath("$.message", is(APIErrors.USER_NOT_IN_DATABASE.toString())));
		}
		
		@Test
		@DisplayName("should return 400 when invalid display name")
		public void changeToInvalidName() throws Exception {
			dto = new UserNameChangeDto(1L, "aa");
			jsonContent = new ObjectMapper().writeValueAsString(dto);
			
			final ResultActions result = mockMvc.perform(put("/api/users/1/display-name")
					.contentType(MediaType.APPLICATION_JSON).content(jsonContent));

			result.andExpect(status().isBadRequest())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.VALIDATION_ERROR.toString())))
					.andExpect(jsonPath("$.message", is(APIErrors.DISPLAY_NAME_TOO_SHORT.toString())));
		}
		
		@Test
		@DisplayName("should return 400 when name already taken")
		public void changeNameToAlradyTakenOne() throws Exception {
			dto = new UserNameChangeDto(3L, "user_2");
			jsonContent = new ObjectMapper().writeValueAsString(dto);
			
			final ResultActions result = mockMvc.perform(put("/api/users/3/display-name")
					.contentType(MediaType.APPLICATION_JSON).content(jsonContent));

			result.andExpect(status().isBadRequest())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.VALIDATION_ERROR.toString())))
					.andExpect(jsonPath("$.message", is(APIErrors.DISPLAY_NAME_ALREADY_TAKEN.toString())));
		}

	}

	@Nested
	@DisplayName("change password")
	class ChangePasswordEndpoint {

		private UserPasswordChangeDto dto;
		private String jsonContent;

		@Test
		@DisplayName("should return no content when success")
		public void changeWhenValidData() throws Exception {
			dto = new UserPasswordChangeDto(1L, "test", "validPass1");
			jsonContent = new ObjectMapper().writeValueAsString(dto);

			final ResultActions result = mockMvc
					.perform(put("/api/users/1/password").contentType(MediaType.APPLICATION_JSON).content(jsonContent));

			result.andExpect(status().isNoContent());
		}
		
		@Test
		@DisplayName("should return 404 when user does not exist")
		public void changeWhenInvalidId() throws Exception {
			dto = new UserPasswordChangeDto(23L, "test", "validPass1");
			jsonContent = new ObjectMapper().writeValueAsString(dto);
			
			final ResultActions result = mockMvc.perform(put("/api/users/23/password")
					.contentType(MediaType.APPLICATION_JSON).content(jsonContent));

			result.andExpect(status().isNotFound())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.RESOURCE_NOT_FOUND.toString())))
					.andExpect(jsonPath("$.message", is(APIErrors.USER_NOT_IN_DATABASE.toString())));
		}

		@ParameterizedTest
		@DisplayName("should return 400 and when invalid new password")
		@ValueSource(strings = { "1234;PASSWORD_TOO_SHORT", "abbbcccdeee;PASSWORD_HAS_NO_CAPITAL_LETTERS",
				"inValidPassword;PASSWORD_HAS_NO_SPECIAL_CHARS" })
		public void changeWhenInvalidNewPassword(String passAndError) throws Exception {
			String[] parts = passAndError.split(";");
			dto = new UserPasswordChangeDto(3L, "test", parts[0]);
			jsonContent = new ObjectMapper().writeValueAsString(dto);

			final ResultActions result = mockMvc.perform(put("/api/users/3/password")
							.contentType(MediaType.APPLICATION_JSON).content(jsonContent));
			
			result.andExpect(status().isBadRequest())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.VALIDATION_ERROR.toString())))
					.andExpect(jsonPath("$.message", is(parts[1])));
		}

		@Test
		@DisplayName("should return 400 and when old password does not match")
		public void changeWhenOldPasswordDoesNotMatch() throws Exception {

			UserPasswordChangeDto dto = new UserPasswordChangeDto(1L, "test1", "validPass1");
			jsonContent = new ObjectMapper().writeValueAsString(dto);

			final ResultActions result = mockMvc.perform(put("/api/users/1/password")
							.contentType(MediaType.APPLICATION_JSON).content(jsonContent));
			
			result.andExpect(status().isBadRequest())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.type", is(APIErrorsTypes.VALIDATION_ERROR.toString())))
					.andExpect(jsonPath("$.message", is(APIErrors.PASSWORDS_DO_NOT_MATCH.toString())));
		}
	}

}
