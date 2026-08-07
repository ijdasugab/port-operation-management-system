package com.portops.common.dto;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Generic API response wrapper.
 */
@Builder
public record ApiResponse<T>(
		boolean success,
		String message,
		T data,
		LocalDateTime timestamp
) {
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(true, message, data, LocalDateTime.now());
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, "Operation successful", data, LocalDateTime.now());
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null, LocalDateTime.now());
	}
}
