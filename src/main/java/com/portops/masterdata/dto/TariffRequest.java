package com.portops.masterdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TariffRequest(
		@NotNull(message = "Service Type ID is required") UUID serviceTypeId,
		@NotBlank(message = "Tariff Code is required") String tariffCode,
		@NotBlank(message = "Description is required") String description,
		@NotNull(message = "Unit Price is required") BigDecimal unitPrice,
		@NotBlank(message = "Unit is required") String unit,
		String currency,
		@NotNull(message = "Effective From date is required") LocalDate effectiveFrom,
		LocalDate effectiveTo
) {}
