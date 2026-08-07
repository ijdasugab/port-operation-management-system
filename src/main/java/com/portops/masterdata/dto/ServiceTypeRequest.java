package com.portops.masterdata.dto;

import jakarta.validation.constraints.NotBlank;

public record ServiceTypeRequest(
		@NotBlank(message = "Code is required") String code,
		@NotBlank(message = "Name is required") String name,
		String description,
		String category
) {}
