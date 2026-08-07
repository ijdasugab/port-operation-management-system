package com.portops.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity to store audit logs for important domain events and state changes.
 */
@Entity
@Table(name = "audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

	@Id
	private UUID id;

	@Column(name = "entity_type", nullable = false)
	private String entityType;

	@Column(name = "entity_id", nullable = false)
	private UUID entityId;

	@Column(name = "action", nullable = false)
	private String action;

	@Column(name = "previous_value", columnDefinition = "TEXT")
	private String previousValue;

	@Column(name = "new_value", columnDefinition = "TEXT")
	private String newValue;

	@Column(name = "performed_by", nullable = false)
	private String performedBy;

	@Column(name = "performed_at", nullable = false)
	private LocalDateTime performedAt;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "details", columnDefinition = "TEXT")
	private String details;
}
