package com.framework.blogapi.controller;

import com.framework.blogapi.dto.UserRequestDTO;
import com.framework.blogapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/users")
public class UserController {
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@RequestBody @Validated UserRequestDTO userDto){

		userService.create(userDto);

	}

}