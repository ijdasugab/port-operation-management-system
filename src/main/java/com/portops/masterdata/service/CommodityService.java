package com.portops.masterdata.service;

import com.portops.common.exception.ResourceNotFoundException;
import com.portops.masterdata.dto.CommodityRequest;
import com.portops.masterdata.dto.CommodityResponse;
import com.portops.masterdata.entity.Commodity;
import com.portops.masterdata.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommodityService {

	private final CommodityRepository repository;

	public Page<CommodityResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(this::mapToResponse);
	}

	public CommodityResponse getById(UUID id) {
		return repository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Commodity not found with id: " + id));
	}

	@Transactional
	public CommodityResponse create(CommodityRequest request) {
		Commodity entity = Commodity.builder()
				.code(request.code())
				.name(request.name())
				.category(request.category())
				.hazardous(request.hazardous())
				.description(request.description())
				.active(true)
				.build();
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public CommodityResponse update(UUID id, CommodityRequest request) {
		Commodity entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Commodity not found with id: " + id));
		entity.setCode(request.code());
		entity.setName(request.name());
		entity.setCategory(request.category());
		entity.setHazardous(request.hazardous());
		entity.setDescription(request.description());
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public CommodityResponse toggleActive(UUID id, boolean active) {
		Commodity entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Commodity not found with id: " + id));
		entity.setActive(active);
		return mapToResponse(repository.save(entity));
	}

	private CommodityResponse mapToResponse(Commodity entity) {
		return new CommodityResponse(
				entity.getId(),
				entity.getCode(),
				entity.getName(),
				entity.getCategory(),
				entity.isHazardous(),
				entity.getDescription(),
				entity.isActive(),
				entity.getCreatedAt()
		);
	}
}
