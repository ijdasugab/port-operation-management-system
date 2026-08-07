package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.masterdata.dto.ServiceTypeRequest;
import com.portops.masterdata.dto.ServiceTypeResponse;
import com.portops.masterdata.service.ServiceTypeService;
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
@RequestMapping("/api/master-data/service-types")
@RequiredArgsConstructor
@Tag(name = "Service Type", description = "Master data API for Service Types")
public class ServiceTypeController {

	private final ServiceTypeService service;

	@GetMapping
	@Operation(summary = "Get all service types")
	public ApiResponse<PageResponse<ServiceTypeResponse>> getAll(Pageable pageable) {
		Page<ServiceTypeResponse> page = service.getAll(pageable);
		return ApiResponse.<PageResponse<ServiceTypeResponse>>builder()
				.success(true)
				.message("Successfully retrieved service types")
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
	@Operation(summary = "Get service type by ID")
	public ApiResponse<ServiceTypeResponse> getById(@PathVariable UUID id) {
		return ApiResponse.<ServiceTypeResponse>builder()
				.success(true)
				.message("Successfully retrieved service type")
				.data(service.getById(id))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create new service type")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<ServiceTypeResponse> create(@RequestBody @Valid ServiceTypeRequest request) {
		return ApiResponse.<ServiceTypeResponse>builder()
				.success(true)
				.message("Successfully created service type")
				.data(service.create(request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update existing service type")
	public ApiResponse<ServiceTypeResponse> update(@PathVariable UUID id, @RequestBody @Valid ServiceTypeRequest request) {
		return ApiResponse.<ServiceTypeResponse>builder()
				.success(true)
				.message("Successfully updated service type")
				.data(service.update(id, request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Toggle active status")
	public ApiResponse<ServiceTypeResponse> toggleActive(@PathVariable UUID id, @RequestParam boolean active) {
		return ApiResponse.<ServiceTypeResponse>builder()
				.success(true)
				.message("Successfully updated service type status")
				.data(service.toggleActive(id, active))
				.timestamp(LocalDateTime.now())
				.build();
	}
}
