package com.portops.identity.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.identity.dto.LoginRequest;
import com.portops.identity.dto.LoginResponse;
import com.portops.identity.dto.RegisterRequest;
import com.portops.identity.dto.UserInfo;
import com.portops.identity.security.SecurityUtils;
import com.portops.identity.service.AuthService;
import com.portops.identity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication Management APIs")
public class AuthController {

	private final AuthService authService;
	private final UserService userService;

	@PostMapping("/login")
	@Operation(summary = "Authenticate user and return JWT token")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ApiResponse<UserInfo> register(@Valid @RequestBody RegisterRequest request) {
		UserInfo userInfo = authService.register(request);
		return new ApiResponse<>(true, "User registered successfully", userInfo, LocalDateTime.now());
	}

	@GetMapping("/me")
	@Operation(summary = "Get current authenticated user profile")
	public ApiResponse<UserInfo> getCurrentUser() {
		UserInfo userInfo = userService.getUserById(SecurityUtils.getCurrentUserId());
		return new ApiResponse<>(true, "Current user retrieved", userInfo, LocalDateTime.now());
	}
}
