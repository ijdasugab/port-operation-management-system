package com.portops.masterdata.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record VehicleRequest(
		@NotBlank(message = "Plate Number is required") String plateNumber,
		@NotBlank(message = "Vehicle Type is required") String vehicleType,
		BigDecimal maxCapacityTons,
		String ownerName
) {}
