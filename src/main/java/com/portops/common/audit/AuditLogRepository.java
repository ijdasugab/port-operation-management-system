package com.portops.common.audit;

import com.portops.common.entity.AuditLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
	List<AuditLog> findByEntityTypeAndEntityIdOrderByPerformedAtDesc(String entityType, UUID entityId);
	Page<AuditLog> findByPerformedByOrderByPerformedAtDesc(String performedBy, Pageable pageable);
}
