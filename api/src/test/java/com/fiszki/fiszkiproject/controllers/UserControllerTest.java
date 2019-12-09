package com.fiszki.fiszkiproject.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
import com.fiszki.fiszkiproject.exceptions.AuthValidatorException;
import com.fiszki.fiszkiproject.exceptions.UserValidatorException;
import com.fiszki.fiszkiproject.exceptions.common.Errors;
import com.fiszki.fiszkiproject.services.UserService;
import com.fiszki.fiszkiproject.services.impl.UserServiceImpl;

@SpringBootTest
@Transactional
public class UserControllerTest {
	
	private MockMvc mockMvc;
	private UserService userService;
	
	@BeforeEach
	public void setUp() {
		userService = Mockito.mock(UserServiceImpl.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
	}
	
	@Nested
	@DisplayName("get all users")
	class UsersEndpoint {
		
		@Test
		@DisplayName("should return empty list when no users in database")
		public void usersEndpointShouldReturnOkWhenThereAreNoUsers() throws Exception {
			when(userService.getAllUsers())
				.thenReturn(new ArrayList<UserBasicInfoDto>());
			
			final MockHttpServletResponse response = mockMvc
					.perform(get("/api/users"))
					.andReturn().getResponse();
			
			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
			assertThat(response.getContentAsString()).isEqualTo("[]");
		}
		
		@Test
		@DisplayName("should return correct json data array with all users")
		public void usersEndpointShouldReturnAnArrayOfUserObjects() throws Exception {
			List<UserBasicInfoDto> users = new ArrayList<>();
			users.add(new UserBasicInfoDto(12L, "user12@test.com", "user_12"));
			users.add(new UserBasicInfoDto(121L, "user121@test.com", "user_121"));
			users.add(new UserBasicInfoDto(456L, "user456@test.com", "user_456"));
			when(userService.getAllUsers()).thenReturn(users);
			
			final ResultActions result = mockMvc.perform(get("/api/users"));
			
			result
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(12)))
				.andExpect(jsonPath("$[1].id", is(121)))
				.andExpect(jsonPath("$[2].id", is(456)))
				.andExpect(jsonPath("$[1].displayName", is("user_121")))
				.andExpect(jsonPath("$[2].email", is("user456@test.com")));
		}		
	}

	
	@Nested
	@DisplayName("get single user")
	class UsersIdEndpoint {
	
		@Test
		@DisplayName("should return 404 when user does not exist")
		public void getInValidUser() throws Exception {
			when(userService.getUserById(1L)).thenReturn(null);
			
			final MockHttpServletResponse response = mockMvc
					.perform(get("/api/users/1"))
					.andReturn().getResponse();
			
			assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
			assertThat(response.getContentAsString()).isEmpty();
		}
		
		@Test
		@DisplayName("should return return correct json data when user exists")
		public void getValidUser() throws Exception {
			when(userService.getUserById(33L))
				.thenReturn(new UserBasicInfoDto(33L, "user33@test.com", "user_33"));
			
			final ResultActions result = mockMvc.perform(get("/api/users/33"));
			
			result
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(33)))
				.andExpect(jsonPath("$.displayName", is("user_33")))
				.andExpect(jsonPath("$.email", is("user33@test.com")));
		}
		
	}
	
	@Nested
	@DisplayName("change displayName")
	class ChangeNameEndpoint {
		
		private UserNameChangeDto dto = new UserNameChangeDto(1L, "validName");;
		private String jsonContent;
		
		@BeforeEach
		void setUp() throws JsonProcessingException {
			jsonContent = new ObjectMapper().writeValueAsString(dto);
		}
		
		@Test
		@DisplayName("should return no content when success")
		public void changeWhenWasOk() throws Exception {		
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenReturn(true);
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/displayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isNoContent());
		}
		
		@Test
		@DisplayName("should return 404 when user does not exist")
		public void changeWithInvalidUserId() throws Exception {	
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenReturn(false);
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/displayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isNotFound());
		}
		
		@Test
		@DisplayName("should return 400 when invalid display name")
		public void changeToInvalidName() throws Exception {		
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenThrow(new UserValidatorException(Errors.DISPLAY_NAME_TOO_SHORT));
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/displayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.type", is("UserValidatorException")))
				.andExpect(jsonPath("$.message", is(Errors.DISPLAY_NAME_TOO_SHORT.toString())));
		}		
		
		@Test
		@DisplayName("should return 400 when name already taken")
		public void changeNameToAlradyTakenOne() throws Exception {		
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenThrow(new UserValidatorException(Errors.DISPLAY_NAME_ALREADY_TAKEN));
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/displayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.type", is("UserValidatorException")))
				.andExpect(jsonPath("$.message", is(Errors.DISPLAY_NAME_ALREADY_TAKEN.toString())));
		}	
		
	}
	
	@Nested
	@DisplayName("change password")
	class ChangePasswordEndpoint {
		
		private UserPasswordChangeDto dto = new UserPasswordChangeDto(1L, "test", "validPass1");
		private String jsonContent;
		
		@BeforeEach
		void setUp() throws JsonProcessingException {
			jsonContent = new ObjectMapper().writeValueAsString(dto);
		}	
		
		@Test
		@DisplayName("should return no content when success")
		public void changeWhenValidData() throws Exception {		
			when(userService.changePassword(any(UserPasswordChangeDto.class)))
				.thenReturn(true);
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/password")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isNoContent());
		}		
		
		@Test
		@DisplayName("should return 404 when user does not exist")
		public void changeWhenInvalidId() throws Exception {		
			when(userService.changePassword(any(UserPasswordChangeDto.class)))
				.thenReturn(false);
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/password")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isNotFound());
		}	
		
		@Test
		@DisplayName("should return 400 when invalid old password")
		public void changeWhenInvalidOldPassword() throws Exception {		
			when(userService.changePassword(any(UserPasswordChangeDto.class)))
				.thenThrow(new AuthValidatorException(Errors.INVALID_PASSWORD));
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/password")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isBadRequest());
		}

		@ParameterizedTest
		@DisplayName("should return 400 and when invalid new password")
		@EnumSource(Errors.class)
		public void changeWhenInvalidNewPassword(Errors error) throws Exception {		
			when(userService.changePassword(any(UserPasswordChangeDto.class)))
				.thenThrow(new UserValidatorException(error));
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/password")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.type", is("UserValidatorException")))
				.andExpect(jsonPath("$.message", is(error.toString())));
		}
		
		@Test
		@DisplayName("should return 400 and when old password does not match")
		public void changeWhenOldPasswordDoesNotMatch() throws Exception {		
			when(userService.changePassword(any(UserPasswordChangeDto.class)))
				.thenThrow(new AuthValidatorException(Errors.INVALID_PASSWORD));
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/password")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.type", is("AuthValidatorException")))
				.andExpect(jsonPath("$.message", is(Errors.INVALID_PASSWORD.toString())));
		}
	}

	
}
