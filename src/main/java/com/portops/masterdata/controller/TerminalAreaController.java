package com.portops.masterdata.controller;

import com.portops.common.dto.ApiResponse;
import com.portops.common.dto.PageResponse;
import com.portops.masterdata.dto.TerminalAreaRequest;
import com.portops.masterdata.dto.TerminalAreaResponse;
import com.portops.masterdata.service.TerminalAreaService;
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
@RequestMapping("/api/master-data/terminal-areas")
@RequiredArgsConstructor
@Tag(name = "Terminal Area", description = "Master data API for Terminal Areas")
public class TerminalAreaController {

	private final TerminalAreaService service;

	@GetMapping
	@Operation(summary = "Get all terminal areas")
	public ApiResponse<PageResponse<TerminalAreaResponse>> getAll(Pageable pageable) {
		Page<TerminalAreaResponse> page = service.getAll(pageable);
		return ApiResponse.<PageResponse<TerminalAreaResponse>>builder()
				.success(true)
				.message("Successfully retrieved terminal areas")
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
	@Operation(summary = "Get terminal area by ID")
	public ApiResponse<TerminalAreaResponse> getById(@PathVariable UUID id) {
		return ApiResponse.<TerminalAreaResponse>builder()
				.success(true)
				.message("Successfully retrieved terminal area")
				.data(service.getById(id))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create new terminal area")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<TerminalAreaResponse> create(@RequestBody @Valid TerminalAreaRequest request) {
		return ApiResponse.<TerminalAreaResponse>builder()
				.success(true)
				.message("Successfully created terminal area")
				.data(service.create(request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update existing terminal area")
	public ApiResponse<TerminalAreaResponse> update(@PathVariable UUID id, @RequestBody @Valid TerminalAreaRequest request) {
		return ApiResponse.<TerminalAreaResponse>builder()
				.success(true)
				.message("Successfully updated terminal area")
				.data(service.update(id, request))
				.timestamp(LocalDateTime.now())
				.build();
	}

	@PatchMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Toggle active status")
	public ApiResponse<TerminalAreaResponse> toggleActive(@PathVariable UUID id, @RequestParam boolean active) {
		return ApiResponse.<TerminalAreaResponse>builder()
				.success(true)
				.message("Successfully updated terminal area status")
				.data(service.toggleActive(id, active))
				.timestamp(LocalDateTime.now())
				.build();
	}
}
