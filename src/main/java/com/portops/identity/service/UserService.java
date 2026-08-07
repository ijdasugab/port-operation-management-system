package com.portops.identity.service;

import com.portops.common.enums.UserRole;
import com.portops.common.exception.BusinessRuleException;
import com.portops.common.exception.ResourceNotFoundException;
import com.portops.identity.dto.ChangePasswordRequest;
import com.portops.identity.dto.UpdateUserRequest;
import com.portops.identity.dto.UserInfo;
import com.portops.identity.entity.User;
import com.portops.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UserInfo> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable).map(this::mapToUserInfo);
	}

	@Transactional(readOnly = true)
	public UserInfo getUserById(UUID id) {
		return userRepository.findById(id)
				.map(this::mapToUserInfo)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Transactional
	public UserInfo updateUser(UUID id, UpdateUserRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
			throw new BusinessRuleException("Email is already in use");
		}

		user.setEmail(request.email());
		user.setFullName(request.fullName());
		
		if (request.enabled() != null) {
			user.setEnabled(request.enabled());
		}

		return mapToUserInfo(userRepository.save(user));
	}

	@Transactional
	public void changePassword(UUID id, ChangePasswordRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
			throw new BusinessRuleException("Current password does not match");
		}

		user.setPassword(passwordEncoder.encode(request.newPassword()));
		userRepository.save(user);
	}

	@Transactional
	public UserInfo toggleUserStatus(UUID id, boolean status) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		user.setEnabled(status);
		return mapToUserInfo(userRepository.save(user));
	}

	@Transactional(readOnly = true)
	public Page<UserInfo> getUsersByRole(UserRole role, Pageable pageable) {
		return userRepository.findByRole(role, pageable).map(this::mapToUserInfo);
	}

	private UserInfo mapToUserInfo(User user) {
		return UserInfo.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.fullName(user.getFullName())
				.role(user.getRole().name())
				.build();
	}
}
