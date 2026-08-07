package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.masterdata.dto.VehicleRequest;
import com.portops.masterdata.dto.VehicleResponse;
import com.portops.masterdata.service.VehicleService;
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
@RequestMapping("/api/master-data/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicle", description = "Master data API for Vehicles")
public class VehicleController {

	private final VehicleService service;

	@GetMapping
	@Operation(summary = "Get all vehicles")
	public ApiResponse<PageResponse<VehicleResponse>> getAll(Pageable pageable) {
		Page<VehicleResponse> page = service.getAll(pageable);
		return ApiResponse.<PageResponse<VehicleResponse>>builder()
				.success(true)
				.message("Successfully retrieved vehicles")
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
	@Operation(summary = "Get vehicle by ID")
	public ApiResponse<VehicleResponse> getById(@PathVariable UUID id) {
		return ApiResponse.<VehicleResponse>builder()
				.success(true)
				.message("Successfully retrieved vehicle")
				.data(service.getById(id))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create new vehicle")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<VehicleResponse> create(@RequestBody @Valid VehicleRequest request) {
		return ApiResponse.<VehicleResponse>builder()
				.success(true)
				.message("Successfully created vehicle")
				.data(service.create(request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update existing vehicle")
	public ApiResponse<VehicleResponse> update(@PathVariable UUID id, @RequestBody @Valid VehicleRequest request) {
		return ApiResponse.<VehicleResponse>builder()
				.success(true)
				.message("Successfully updated vehicle")
				.data(service.update(id, request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Toggle active status")
	public ApiResponse<VehicleResponse> toggleActive(@PathVariable UUID id, @RequestParam boolean active) {
		return ApiResponse.<VehicleResponse>builder()
				.success(true)
				.message("Successfully updated vehicle status")
				.data(service.toggleActive(id, active))
				.timestamp(LocalDateTime.now())
				.build();
	}
}
