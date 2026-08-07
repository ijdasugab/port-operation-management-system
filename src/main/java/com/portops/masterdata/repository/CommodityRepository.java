package com.portops.masterdata.repository;

import com.portops.masterdata.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, UUID> {
	Optional<Commodity> findByCode(String code);
	List<Commodity> findByActiveTrue();
	List<Commodity> findByCategory(String category);
}
