package com.portops.masterdata.service;

import com.portops.common.exception.ResourceNotFoundException;
import com.portops.masterdata.dto.TariffRequest;
import com.portops.masterdata.dto.TariffResponse;
import com.portops.masterdata.entity.ServiceType;
import com.portops.masterdata.entity.Tariff;
import com.portops.masterdata.repository.ServiceTypeRepository;
import com.portops.masterdata.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TariffService {

	private final TariffRepository repository;
	private final ServiceTypeRepository serviceTypeRepository;
	private final ServiceTypeService serviceTypeService;

	public Page<TariffResponse> getAll(Pageable pageable) {
		return repository.findAll(pageable).map(this::mapToResponse);
	}

	public TariffResponse getById(UUID id) {
		return repository.findById(id)
				.map(this::mapToResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Tariff not found with id: " + id));
	}

	@Transactional
	public TariffResponse create(TariffRequest request) {
		ServiceType serviceType = serviceTypeRepository.findById(request.serviceTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("ServiceType not found with id: " + request.serviceTypeId()));

		Tariff entity = Tariff.builder()
				.serviceType(serviceType)
				.tariffCode(request.tariffCode())
				.description(request.description())
				.unitPrice(request.unitPrice())
				.unit(request.unit())
				.currency(request.currency() != null ? request.currency() : "IDR")
				.effectiveFrom(request.effectiveFrom())
				.effectiveTo(request.effectiveTo())
				.active(true)
				.build();
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public TariffResponse update(UUID id, TariffRequest request) {
		Tariff entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tariff not found with id: " + id));
		
		if (!entity.getServiceType().getId().equals(request.serviceTypeId())) {
			ServiceType serviceType = serviceTypeRepository.findById(request.serviceTypeId())
					.orElseThrow(() -> new ResourceNotFoundException("ServiceType not found with id: " + request.serviceTypeId()));
			entity.setServiceType(serviceType);
		}

		entity.setTariffCode(request.tariffCode());
		entity.setDescription(request.description());
		entity.setUnitPrice(request.unitPrice());
		entity.setUnit(request.unit());
		entity.setCurrency(request.currency() != null ? request.currency() : "IDR");
		entity.setEffectiveFrom(request.effectiveFrom());
		entity.setEffectiveTo(request.effectiveTo());
		return mapToResponse(repository.save(entity));
	}

	@Transactional
	public TariffResponse toggleActive(UUID id, boolean active) {
		Tariff entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tariff not found with id: " + id));
		entity.setActive(active);
		return mapToResponse(repository.save(entity));
	}

	private TariffResponse mapToResponse(Tariff entity) {
		return new TariffResponse(
				entity.getId(),
				serviceTypeService.mapToResponse(entity.getServiceType()),
				entity.getTariffCode(),
				entity.getDescription(),
				entity.getUnitPrice(),
				entity.getUnit(),
				entity.getCurrency(),
				entity.getEffectiveFrom(),
				entity.getEffectiveTo(),
				entity.isActive(),
				entity.getCreatedAt()
		);
	}
}
