package com.portops.masterdata.service;

import com.portops.common.exception.ResourceNotFoundException;
import com.portops.masterdata.dto.TerminalAreaRequest;
import com.portops.masterdata.dto.TerminalAreaResponse;
import com.portops.masterdata.entity.TerminalArea;
import com.portops.masterdata.repository.TerminalAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TerminalAreaService {

	private final TerminalAreaRepository repository;

	public Page<TerminalAreaResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(this::mapToResponse);
	}

	public TerminalAreaResponse getById(UUID id) {
		return repository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(() -> new ResourceNotFoundException("TerminalArea not found with id: " + id));
	}

	@Transactional
	public TerminalAreaResponse create(TerminalAreaRequest request) {
		TerminalArea entity = TerminalArea.builder()
				.code(request.code())
				.name(request.name())
				.areaType(request.areaType())
				.capacity(request.capacity())
				.capacityUnit(request.capacityUnit())
				.location(request.location())
				.active(true)
				.build();
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public TerminalAreaResponse update(UUID id, TerminalAreaRequest request) {
		TerminalArea entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TerminalArea not found with id: " + id));
		entity.setCode(request.code());
		entity.setName(request.name());
		entity.setAreaType(request.areaType());
		entity.setCapacity(request.capacity());
		entity.setCapacityUnit(request.capacityUnit());
		entity.setLocation(request.location());
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public TerminalAreaResponse toggleActive(UUID id, boolean active) {
		TerminalArea entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TerminalArea not found with id: " + id));
		entity.setActive(active);
		return mapToResponse(repository.save(entity));
	}

	private TerminalAreaResponse mapToResponse(TerminalArea entity) {
		return new TerminalAreaResponse(
				entity.getId(),
				entity.getCode(),
				entity.getName(),
				entity.getAreaType(),
				entity.getCapacity(),
				entity.getCapacityUnit(),
				entity.getLocation(),
				entity.isActive(),
				entity.getCreatedAt()
		);
	}
}
