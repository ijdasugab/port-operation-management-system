package com.portops.masterdata.service;

import com.portops.common.exception.ResourceNotFoundException;
import com.portops.masterdata.dto.ServiceTypeRequest;
import com.portops.masterdata.dto.ServiceTypeResponse;
import com.portops.masterdata.entity.ServiceType;
import com.portops.masterdata.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {

	private final ServiceTypeRepository repository;

	public Page<ServiceTypeResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(this::mapToResponse);
	}

	public ServiceTypeResponse getById(UUID id) {
		return repository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(() -> new ResourceNotFoundException("ServiceType not found with id: " + id));
	}

	@Transactional
	public ServiceTypeResponse create(ServiceTypeRequest request) {
		ServiceType entity = ServiceType.builder()
				.code(request.code())
				.name(request.name())
				.description(request.description())
				.category(request.category())
				.active(true)
				.build();
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public ServiceTypeResponse update(UUID id, ServiceTypeRequest request) {
		ServiceType entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ServiceType not found with id: " + id));
		entity.setCode(request.code());
		entity.setName(request.name());
		entity.setDescription(request.description());
		entity.setCategory(request.category());
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public ServiceTypeResponse toggleActive(UUID id, boolean active) {
		ServiceType entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ServiceType not found with id: " + id));
		entity.setActive(active);
		return mapToResponse(repository.save(entity));
	}

	public ServiceTypeResponse mapToResponse(ServiceType entity) {
		return new ServiceTypeResponse(
				entity.getId(),
				entity.getCode(),
				entity.getName(),
				entity.getDescription(),
				entity.getCategory(),
				entity.isActive(),
				entity.getCreatedAt()
		);
	}
}
