package com.portops.identity.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.common.enums.UserRole;
import com.portops.identity.dto.ChangePasswordRequest;
import com.portops.identity.dto.UpdateUserRequest;
import com.portops.identity.dto.UserInfo;
import com.portops.identity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User Management APIs")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

	private final UserService userService;

	@GetMapping
	@Operation(summary = "Get all users, optionally filter by role")
	public PageResponse<UserInfo> getAllUsers(
			@RequestParam(required = false) UserRole role,
			Pageable pageable) {
		
		Page<UserInfo> page = role != null 
				? userService.getUsersByRole(role, pageable) 
				: userService.getAllUsers(pageable);

		return new PageResponse<>(true, "Users retrieved", page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by ID")
	public ApiResponse<UserInfo> getUserById(@PathVariable UUID id) {
		UserInfo userInfo = userService.getUserById(id);
		return new ApiResponse<>(true, "User retrieved successfully", userInfo, LocalDateTime.now());
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user details")
	public ApiResponse<UserInfo> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
		UserInfo userInfo = userService.updateUser(id, request);
		return new ApiResponse<>(true, "User updated successfully", userInfo, LocalDateTime.now());
	}

	@PatchMapping("/{id}/status")
	@Operation(summary = "Enable or disable user")
	public ApiResponse<UserInfo> toggleUserStatus(
			@PathVariable UUID id, 
			@RequestParam boolean enabled) {
		UserInfo userInfo = userService.toggleUserStatus(id, enabled);
		return new ApiResponse<>(true, "User status updated successfully", userInfo, LocalDateTime.now());
	}

	@PostMapping("/{id}/change-password")
	@PreAuthorize("hasRole('ADMIN') or T(com.portops.identity.security.SecurityUtils).getCurrentUserId() == #id")
	@Operation(summary = "Change user password")
	public ApiResponse<Void> changePassword(
			@PathVariable UUID id, 
			@Valid @RequestBody ChangePasswordRequest request) {
		userService.changePassword(id, request);
		return new ApiResponse<>(true, "Password changed successfully", null, LocalDateTime.now());
	}
}
