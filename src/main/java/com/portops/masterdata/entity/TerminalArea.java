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

@Entity
@Table(name = "terminal_areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TerminalArea extends BaseEntity {

	@Column(nullable = false, unique = true, length = 100)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(name = "area_type", nullable = false, length = 100)
	private String areaType;

	private Integer capacity;

	@Column(name = "capacity_unit", length = 50)
	private String capacityUnit;

	private String location;

	@Builder.Default
	@Column(nullable = false)
	private boolean active = true;
}
