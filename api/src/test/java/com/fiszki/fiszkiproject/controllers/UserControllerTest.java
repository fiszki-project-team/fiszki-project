package com.fiszki.fiszkiproject.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
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
	  
	@Test
	public void usersEndpointShouldReturnOkWhenThereAreNoUsers() throws Exception {
		when(userService.getAllUsers()).thenReturn(new ArrayList<UserBasicInfoDto>());
		final ResultActions result = mockMvc.perform(get("/api/users"));
		
		result
			.andExpect(status().isOk());
	}
	
	@Test
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
			.andExpect(jsonPath("$[0].id", is(12)))
			.andExpect(jsonPath("$[1].id", is(121)))
			.andExpect(jsonPath("$[2].id", is(456)))
			.andExpect(jsonPath("$[1].displayName", is("user_121")))
			.andExpect(jsonPath("$[2].email", is("user456@test.com")));
	}
	
	@Test
	public void singleUserEndpointShouldReturnNullWhenUserDoesNotExists() throws Exception {
		when(userService.getUserById(1L)).thenReturn(null);
		
		final ResultActions result = mockMvc.perform(get("/api/users/1"));
		
		result
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void singleUserEndpointShouldReturnValidUserData() throws Exception {
		when(userService.getUserById(33L)).thenReturn(new UserBasicInfoDto(33L, "user33@test.com", "user_33"));
		
		final ResultActions result = mockMvc.perform(get("/api/users/33"));
		
		result
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(33)))
			.andExpect(jsonPath("$.displayName", is("user_33")))
			.andExpect(jsonPath("$.email", is("user33@test.com")));
	}
	
	
}
