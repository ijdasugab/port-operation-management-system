package com.portops.common.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standardized error response structure.
 */
public record ErrorResponse(
		LocalDateTime timestamp,
		int status,
		String error,
		String message,
		String path,
		List<String> details
) {}
