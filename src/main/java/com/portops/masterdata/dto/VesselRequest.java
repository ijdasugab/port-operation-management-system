package com.portops.masterdata.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record VesselRequest(
		@NotBlank(message = "Name is required") String name,
		String imoNumber,
		String flag,
		String vesselType,
		BigDecimal grossTonnage,
		BigDecimal lengthOverall,
		BigDecimal draft
) {}
