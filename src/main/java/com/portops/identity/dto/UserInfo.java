package com.portops.identity.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record UserInfo(
		UUID id,
		String username,
		String email,
		String fullName,
		String role
) {
}
