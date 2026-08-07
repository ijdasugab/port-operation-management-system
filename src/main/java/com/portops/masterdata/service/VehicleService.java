package com.portops.masterdata.service;

import com.portops.common.exception.ResourceNotFoundException;
import com.portops.masterdata.dto.VehicleRequest;
import com.portops.masterdata.dto.VehicleResponse;
import com.portops.masterdata.entity.Vehicle;
import com.portops.masterdata.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

	private final VehicleRepository repository;

	public Page<VehicleResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(this::mapToResponse);
	}

	public VehicleResponse getById(UUID id) {
		return repository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
	}

	@Transactional
	public VehicleResponse create(VehicleRequest request) {
		Vehicle entity = Vehicle.builder()
				.plateNumber(request.plateNumber())
				.vehicleType(request.vehicleType())
				.maxCapacityTons(request.maxCapacityTons())
				.ownerName(request.ownerName())
				.active(true)
				.build();
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public VehicleResponse update(UUID id, VehicleRequest request) {
		Vehicle entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
		entity.setPlateNumber(request.plateNumber());
		entity.setVehicleType(request.vehicleType());
		entity.setMaxCapacityTons(request.maxCapacityTons());
		entity.setOwnerName(request.ownerName());
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public VehicleResponse toggleActive(UUID id, boolean active) {
		Vehicle entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
		entity.setActive(active);
		return mapToResponse(repository.save(entity));
	}

	private VehicleResponse mapToResponse(Vehicle entity) {
		return new VehicleResponse(
				entity.getId(),
				entity.getPlateNumber(),
				entity.getVehicleType(),
				entity.getMaxCapacityTons(),
				entity.getOwnerName(),
				entity.isActive(),
				entity.getCreatedAt()
		);
	}
}
