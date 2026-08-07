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
@Table(name = "vessels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vessel extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(name = "imo_number", unique = true, length = 50)
	private String imoNumber;

	@Column(length = 100)
	private String flag;

	@Column(name = "vessel_type", length = 100)
	private String vesselType;

	@Column(name = "gross_tonnage", precision = 19, scale = 4)
	private BigDecimal grossTonnage;

	@Column(name = "length_overall", precision = 19, scale = 4)
	private BigDecimal lengthOverall;

	@Column(precision = 19, scale = 4)
	private BigDecimal draft;

	@Builder.Default
	@Column(nullable = false)
	private boolean active = true;
}
