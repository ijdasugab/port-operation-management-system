package com.portops.masterdata.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponse(
		UUID id,
		String plateNumber,
		String vehicleType,
		BigDecimal maxCapacityTons,
		String ownerName,
		boolean active,
		LocalDateTime createdAt
) {}
