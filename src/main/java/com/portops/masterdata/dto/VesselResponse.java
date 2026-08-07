package com.portops.masterdata.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VesselResponse(
		UUID id,
		String name,
		String imoNumber,
		String flag,
		String vesselType,
		BigDecimal grossTonnage,
		BigDecimal lengthOverall,
		BigDecimal draft,
		boolean active,
		LocalDateTime createdAt
) {}
