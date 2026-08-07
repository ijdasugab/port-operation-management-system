package com.portops.masterdata.repository;

import com.portops.masterdata.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
	Optional<Vehicle> findByPlateNumber(String plateNumber);
	List<Vehicle> findByActiveTrue();
	List<Vehicle> findByVehicleType(String vehicleType);
}
