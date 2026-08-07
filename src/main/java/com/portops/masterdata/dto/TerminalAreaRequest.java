package com.portops.masterdata.dto;

import jakarta.validation.constraints.NotBlank;

public record TerminalAreaRequest(
		@NotBlank(message = "Code is required") String code,
		@NotBlank(message = "Name is required") String name,
		@NotBlank(message = "Area Type is required") String areaType,
		Integer capacity,
		String capacityUnit,
		String location
) {}
