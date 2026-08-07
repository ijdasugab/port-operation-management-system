package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.masterdata.dto.CommodityRequest;
import com.portops.masterdata.dto.CommodityResponse;
import com.portops.masterdata.service.CommodityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/master-data/commodities")
@RequiredArgsConstructor
@Tag(name = "Commodity", description = "Master data API for Commodities")
public class CommodityController {

	private final CommodityService service;

	@GetMapping
	@Operation(summary = "Get all commodities")
	public ApiResponse<PageResponse<CommodityResponse>> getAll(Pageable pageable) {
		Page<CommodityResponse> page = service.getAll(pageable);
		return ApiResponse.<PageResponse<CommodityResponse>>builder()
				.success(true)
				.message("Successfully retrieved commodities")
				.data(new PageResponse<>(
						page.getContent(),
						page.getNumber(),
						page.getSize(),
						page.getTotalElements(),
						page.getTotalPages(),
						page.isLast()
				))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get commodity by ID")
	public ApiResponse<CommodityResponse> getById(@PathVariable UUID id) {
		return ApiResponse.<CommodityResponse>builder()
				.success(true)
				.message("Successfully retrieved commodity")
				.data(service.getById(id))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create new commodity")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<CommodityResponse> create(@RequestBody @Valid CommodityRequest request) {
		return ApiResponse.<CommodityResponse>builder()
				.success(true)
				.message("Successfully created commodity")
				.data(service.create(request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update existing commodity")
	public ApiResponse<CommodityResponse> update(@PathVariable UUID id, @RequestBody @Valid CommodityRequest request) {
		return ApiResponse.<CommodityResponse>builder()
				.success(true)
				.message("Successfully updated commodity")
				.data(service.update(id, request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Toggle active status")
	public ApiResponse<CommodityResponse> toggleActive(@PathVariable UUID id, @RequestParam boolean active) {
		return ApiResponse.<CommodityResponse>builder()
				.success(true)
				.message("Successfully updated commodity status")
				.data(service.toggleActive(id, active))
				.timestamp(LocalDateTime.now())
				.build();
	}
}
