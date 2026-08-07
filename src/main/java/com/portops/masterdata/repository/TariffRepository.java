package com.portops.masterdata.repository;

import com.portops.masterdata.entity.ServiceType;
import com.portops.masterdata.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, UUID> {
	Optional<Tariff> findByTariffCode(String tariffCode);
	List<Tariff> findByServiceType(ServiceType serviceType);
	List<Tariff> findByServiceTypeAndActiveTrue(ServiceType serviceType);
	
	@Query("SELECT t FROM Tariff t WHERE t.active = true AND t.effectiveFrom <= :date AND (t.effectiveTo IS NULL OR t.effectiveTo >= :date)")
	List<Tariff> findActiveTariffsByDate(@Param("date") LocalDate date);
}
