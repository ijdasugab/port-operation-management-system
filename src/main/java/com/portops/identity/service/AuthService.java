package com.portops.identity.service;

import com.portops.common.audit.AuditService;
import com.portops.common.enums.UserRole;
import com.portops.common.exception.BusinessRuleException;
import com.portops.identity.dto.LoginRequest;
import com.portops.identity.dto.LoginResponse;
import com.portops.identity.dto.RegisterRequest;
import com.portops.identity.dto.UserInfo;
import com.portops.identity.entity.User;
import com.portops.identity.repository.UserRepository;
import com.portops.identity.security.CustomUserDetails;
import com.portops.identity.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuditService auditService;

	@Transactional
	public LoginResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		User user = userRepository.findById(userDetails.getId())
				.orElseThrow(() -> new BusinessRuleException("User not found"));
		user.setLastLoginAt(LocalDateTime.now());
		userRepository.save(user);

		String jwt = tokenProvider.generateToken(userDetails);

		UserInfo userInfo = UserInfo.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.fullName(user.getFullName())
				.role(user.getRole().name())
				.build();

		auditService.log("USER_LOGIN", "User logged in: " + user.getUsername());

		return new LoginResponse(jwt, tokenProvider.getJwtExpirationMs(), userInfo);
	}

	@Transactional
	public UserInfo register(RegisterRequest request) {
		if (userRepository.existsByUsername(request.username())) {
			throw new BusinessRuleException("Username is already taken");
		}
		if (userRepository.existsByEmail(request.email())) {
			throw new BusinessRuleException("Email is already in use");
		}

		UserRole role;
		try {
			role = UserRole.valueOf(request.role().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BusinessRuleException("Invalid role specified");
		}

		User user = User.builder()
				.username(request.username())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.fullName(request.fullName())
				.role(role)
				.enabled(true)
				.build();

		User savedUser = userRepository.save(user);

		auditService.log("USER_REGISTER", "New user registered: " + savedUser.getUsername());

		return UserInfo.builder()
				.id(savedUser.getId())
				.username(savedUser.getUsername())
				.email(savedUser.getEmail())
				.fullName(savedUser.getFullName())
				.role(savedUser.getRole().name())
				.build();
	}
}
