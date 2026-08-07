package com.portops.identity.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
		String token,
		String tokenType,
		long expiresIn,
		UserInfo user
) {
	public LoginResponse(String token, long expiresIn, UserInfo user) {
		this(token, "Bearer", expiresIn, user);
	}
}
