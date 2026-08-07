package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.masterdata.dto.TariffRequest;
import com.portops.masterdata.dto.TariffResponse;
import com.portops.masterdata.service.TariffService;
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
@RequestMapping("/api/master-data/tariffs")
@RequiredArgsConstructor
@Tag(name = "Tariff", description = "Master data API for Tariffs")
public class TariffController {

	private final TariffService service;

	@GetMapping
	@Operation(summary = "Get all tariffs")
	public ApiResponse<PageResponse<TariffResponse>> getAll(Pageable pageable) {
		Page<TariffResponse> page = service.getAll(pageable);
		return ApiResponse.<PageResponse<TariffResponse>>builder()
				.success(true)
				.message("Successfully retrieved tariffs")
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
	@Operation(summary = "Get tariff by ID")
	public ApiResponse<TariffResponse> getById(@PathVariable UUID id) {
		return ApiResponse.<TariffResponse>builder()
				.success(true)
				.message("Successfully retrieved tariff")
				.data(service.getById(id))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create new tariff")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<TariffResponse> create(@RequestBody @Valid TariffRequest request) {
		return ApiResponse.<TariffResponse>builder()
				.success(true)
				.message("Successfully created tariff")
				.data(service.create(request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update existing tariff")
	public ApiResponse<TariffResponse> update(@PathVariable UUID id, @RequestBody @Valid TariffRequest request) {
		return ApiResponse.<TariffResponse>builder()
				.success(true)
				.message("Successfully updated tariff")
				.data(service.update(id, request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Toggle active status")
	public ApiResponse<TariffResponse> toggleActive(@PathVariable UUID id, @RequestParam boolean active) {
		return ApiResponse.<TariffResponse>builder()
				.success(true)
				.message("Successfully updated tariff status")
				.data(service.toggleActive(id, active))
				.timestamp(LocalDateTime.now())
				.build();
	}
}
