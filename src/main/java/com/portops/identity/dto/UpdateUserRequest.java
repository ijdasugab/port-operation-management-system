package com.portops.identity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
		@Email(message = "Email should be valid")
		@NotBlank(message = "Email is required")
		String email,

		@NotBlank(message = "Full name is required")
		String fullName,

		Boolean enabled
) {
}
