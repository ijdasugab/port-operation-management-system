package com.portops.masterdata.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TerminalAreaResponse(
		UUID id,
		String code,
		String name,
		String areaType,
		Integer capacity,
		String capacityUnit,
		String location,
		boolean active,
		LocalDateTime createdAt
) {}
