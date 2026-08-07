package com.portops.masterdata.entity;

import com.portops.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tariff extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_type_id", nullable = false)
	private ServiceType serviceType;

	@Column(name = "tariff_code", nullable = false, unique = true, length = 100)
	private String tariffCode;

	@Column(nullable = false)
	private String description;

	@Column(name = "unit_price", nullable = false, precision = 19, scale = 4)
	private BigDecimal unitPrice;

	@Column(nullable = false, length = 50)
	private String unit;

	@Builder.Default
	@Column(length = 10)
	private String currency = "IDR";

	@Column(name = "effective_from", nullable = false)
	private LocalDate effectiveFrom;

	@Column(name = "effective_to")
	private LocalDate effectiveTo;

	@Builder.Default
	@Column(nullable = false)
	private boolean active = true;
}
