package com.portops.masterdata.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TariffResponse(
		UUID id,
		ServiceTypeResponse serviceType,
		String tariffCode,
		String description,
		BigDecimal unitPrice,
		String unit,
		String currency,
		LocalDate effectiveFrom,
		LocalDate effectiveTo,
		boolean active,
		LocalDateTime createdAt
) {}
