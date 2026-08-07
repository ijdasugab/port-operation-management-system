package com.portops.masterdata.repository;

import com.portops.masterdata.entity.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, UUID> {
	Optional<Vessel> findByImoNumber(String imoNumber);
	List<Vessel> findByActiveTrue();
	List<Vessel> findByVesselType(String vesselType);
}
