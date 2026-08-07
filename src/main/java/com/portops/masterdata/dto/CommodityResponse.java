package com.portops.masterdata.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommodityResponse(
		UUID id,
		String code,
		String name,
		String category,
		boolean hazardous,
		String description,
		boolean active,
		LocalDateTime createdAt
) {}
