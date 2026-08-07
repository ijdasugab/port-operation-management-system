package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.masterdata.dto.VesselRequest;
import com.portops.masterdata.dto.VesselResponse;
import com.portops.masterdata.service.VesselService;
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
@RequestMapping("/api/master-data/vessels")
@RequiredArgsConstructor
@Tag(name = "Vessel", description = "Master data API for Vessels")
public class VesselController {

	private final VesselService service;

	@GetMapping
	@Operation(summary = "Get all vessels")
	public ApiResponse<PageResponse<VesselResponse>> getAll(Pageable pageable) {
		Page<VesselResponse> page = service.getAll(pageable);
		return ApiResponse.<PageResponse<VesselResponse>>builder()
				.success(true)
				.message("Successfully retrieved vessels")
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
	@Operation(summary = "Get vessel by ID")
	public ApiResponse<VesselResponse> getById(@PathVariable UUID id) {
		return ApiResponse.<VesselResponse>builder()
				.success(true)
				.message("Successfully retrieved vessel")
				.data(service.getById(id))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create new vessel")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<VesselResponse> create(@RequestBody @Valid VesselRequest request) {
		return ApiResponse.<VesselResponse>builder()
				.success(true)
				.message("Successfully created vessel")
				.data(service.create(request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update existing vessel")
	public ApiResponse<VesselResponse> update(@PathVariable UUID id, @RequestBody @Valid VesselRequest request) {
		return ApiResponse.<VesselResponse>builder()
				.success(true)
				.message("Successfully updated vessel")
				.data(service.update(id, request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Toggle active status")
	public ApiResponse<VesselResponse> toggleActive(@PathVariable UUID id, @RequestParam boolean active) {
		return ApiResponse.<VesselResponse>builder()
				.success(true)
				.message("Successfully updated vessel status")
				.data(service.toggleActive(id, active))
				.timestamp(LocalDateTime.now())
				.build();
	}
}
