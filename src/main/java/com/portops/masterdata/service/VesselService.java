package com.portops.masterdata.service;

import com.portops.common.exception.ResourceNotFoundException;
import com.portops.masterdata.dto.VesselRequest;
import com.portops.masterdata.dto.VesselResponse;
import com.portops.masterdata.entity.Vessel;
import com.portops.masterdata.repository.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VesselService {

	private final VesselRepository repository;

	public Page<VesselResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(this::mapToResponse);
	}

	public VesselResponse getById(UUID id) {
		return repository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Vessel not found with id: " + id));
	}

	@Transactional
	public VesselResponse create(VesselRequest request) {
		Vessel entity = Vessel.builder()
				.name(request.name())
				.imoNumber(request.imoNumber())
				.flag(request.flag())
				.vesselType(request.vesselType())
				.grossTonnage(request.grossTonnage())
				.lengthOverall(request.lengthOverall())
				.draft(request.draft())
				.active(true)
				.build();
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public VesselResponse update(UUID id, VesselRequest request) {
		Vessel entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vessel not found with id: " + id));
		entity.setName(request.name());
		entity.setImoNumber(request.imoNumber());
		entity.setFlag(request.flag());
		entity.setVesselType(request.vesselType());
		entity.setGrossTonnage(request.grossTonnage());
		entity.setLengthOverall(request.lengthOverall());
		entity.setDraft(request.draft());
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public VesselResponse toggleActive(UUID id, boolean active) {
		Vessel entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vessel not found with id: " + id));
		entity.setActive(active);
		return mapToResponse(repository.save(entity));
	}

	private VesselResponse mapToResponse(Vessel entity) {
		return new VesselResponse(
				entity.getId(),
				entity.getName(),
				entity.getImoNumber(),
				entity.getFlag(),
				entity.getVesselType(),
				entity.getGrossTonnage(),
				entity.getLengthOverall(),
				entity.getDraft(),
				entity.isActive(),
				entity.getCreatedAt()
		);
	}
}
