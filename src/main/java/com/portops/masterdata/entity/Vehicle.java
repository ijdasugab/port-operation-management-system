package com.portops.masterdata.entity;

import com.portops.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle extends BaseEntity {

	@Column(name = "plate_number", nullable = false, unique = true, length = 50)
	private String plateNumber;

	@Column(name = "vehicle_type", nullable = false, length = 100)
	private String vehicleType;

	@Column(name = "max_capacity_tons", precision = 19, scale = 4)
	private BigDecimal maxCapacityTons;

	@Column(name = "owner_name")
	private String ownerName;

	@Builder.Default
	@Column(nullable = false)
	private boolean active = true;
}
