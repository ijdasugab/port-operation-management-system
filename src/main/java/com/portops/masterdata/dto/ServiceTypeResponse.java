package com.portops.masterdata.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ServiceTypeResponse(
		UUID id,
		String code,
		String name,
		String description,
		String category,
		boolean active,
		LocalDateTime createdAt
) {}
