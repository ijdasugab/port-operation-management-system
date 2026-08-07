package com.portops.identity.security;

import com.portops.common.enums.UserRole;
import com.portops.common.exception.UnauthorizedActionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

	private SecurityUtils() {
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static CustomUserDetails getCurrentUserDetails() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			return (CustomUserDetails) authentication.getPrincipal();
		}
		throw new UnauthorizedActionException("No authenticated user found");
	}

	public static String getCurrentUsername() {
		return getCurrentUserDetails().getUsername();
	}

	public static UUID getCurrentUserId() {
		return getCurrentUserDetails().getId();
	}

	public static boolean hasRole(UserRole role) {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return false;
		}
		return authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_" + role.name()));
	}
}
