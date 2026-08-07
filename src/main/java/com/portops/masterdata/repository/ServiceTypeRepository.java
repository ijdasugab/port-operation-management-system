package com.portops.masterdata.repository;

import com.portops.masterdata.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {
	Optional<ServiceType> findByCode(String code);
	List<ServiceType> findByActiveTrue();
	List<ServiceType> findByCategory(String category);
}
