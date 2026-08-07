package com.portops.common.dto;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Generic page response wrapper.
 */
public record PageResponse<T>(
		boolean success,
		String message,
		List<T> content,
		int page,
		int size,
		long totalElements,
		int totalPages,
		boolean last
) {
	/**
	 * Constructor without success/message fields for direct content wrapping.
	 */
	public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
		this(true, "Operation successful", content, page, size, totalElements, totalPages, last);
	}

	public static <T> PageResponse<T> of(Page<T> page) {
		return new PageResponse<>(
				page.getContent(),
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages(),
				page.isLast()
		);
	}
}
