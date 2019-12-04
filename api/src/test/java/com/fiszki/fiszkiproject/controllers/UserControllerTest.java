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
		public void singleUserEndpointShouldReturnNullWhenUserDoesNotExists() throws Exception {
			when(userService.getUserById(1L)).thenReturn(null);
			
			final MockHttpServletResponse response = mockMvc
					.perform(get("/api/users/1"))
					.andReturn().getResponse();
			
			assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
			assertThat(response.getContentAsString()).isEmpty();
		}
		
		@Test
		@DisplayName("should return correct json data when user exists")
		public void singleUserEndpointShouldReturnValidUserData() throws Exception {
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
		@DisplayName("returns no content when success")
		public void changeDisplayNameEndpointShouldReturnNoContentWhenChangeWasOk() throws Exception {		
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenReturn(true);
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/changeDisplayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isNoContent());
		}
		
		@Test
		@DisplayName("returns 404 when user does not exist")
		public void changeDisplayNameEndpointShouldReturnNotFoundWhenInvalidUserId() throws Exception {	
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenReturn(false);
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/changeDisplayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isNotFound());
		}
		
		@Test
		@DisplayName("returns 400 when invalid display name")
		public void changeDisplayNameEndpointShouldReturnBadRequestWhenInvalidName() throws Exception {		
			when(userService.changeDisplayName(any(UserNameChangeDto.class)))
				.thenThrow(new UserValidatorException(Errors.DISPLAY_NAME_TOO_SHORT));
			
			final ResultActions result = mockMvc
					.perform(put("/api/users/changeDisplayName")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonContent));
			
			result
				.andExpect(status().isBadRequest());
		}		
		
	}

	
}
