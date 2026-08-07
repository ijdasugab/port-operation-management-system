package com.portops.masterdata.repository;

import com.portops.masterdata.entity.TerminalArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TerminalAreaRepository extends JpaRepository<TerminalArea, UUID> {
	Optional<TerminalArea> findByCode(String code);
	List<TerminalArea> findByActiveTrue();
	List<TerminalArea> findByAreaType(String areaType);
}
