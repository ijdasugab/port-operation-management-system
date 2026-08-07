package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.masterdata.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/master-data")
@RequiredArgsConstructor
@Tag(name = "Master Data Overview", description = "Summary of all master data")
public class MasterDataController {

	private final ServiceTypeRepository serviceTypeRepository;
	private final VesselRepository vesselRepository;
	private final CommodityRepository commodityRepository;
	private final TerminalAreaRepository terminalAreaRepository;
	private final VehicleRepository vehicleRepository;
	private final TariffRepository tariffRepository;

	@GetMapping("/summary")
	@Operation(summary = "Get counts of each master data entity type")
	public ApiResponse<Map<String, Long>> getSummary() {
		Map<String, Long> summary = Map.of(
				"serviceTypes", serviceTypeRepository.count(),
				"vessels", vesselRepository.count(),
				"commodities", commodityRepository.count(),
				"terminalAreas", terminalAreaRepository.count(),
				"vehicles", vehicleRepository.count(),
				"tariffs", tariffRepository.count()
		);

		return ApiResponse.<Map<String, Long>>builder()
				.success(true)
				.message("Successfully retrieved master data summary")
				.data(summary)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
