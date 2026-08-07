package com.portops.common.audit;

import com.portops.common.entity.AuditLog;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for recording audit trail entries.
 */
@Service
@RequiredArgsConstructor
public class AuditService {

	private final AuditLogRepository auditLogRepository;

	/**
	 * Full audit log entry with all parameters.
	 */
	@Transactional
	public void log(String entityType, UUID entityId, String action, String performedBy, String previousValue, String newValue, String details) {
		AuditLog auditLog = AuditLog.builder()
				.id(UUID.randomUUID())
				.entityType(entityType)
				.entityId(entityId)
				.action(action)
				.previousValue(previousValue)
				.newValue(newValue)
				.performedBy(performedBy != null ? performedBy : "SYSTEM")
				.performedAt(LocalDateTime.now())
				.details(details)
				.build();
		
		auditLogRepository.save(auditLog);
	}

	/**
	 * Convenience method for simple audit entries without entity ID or value tracking.
	 * Automatically resolves the current user from SecurityContext.
	 */
	@Transactional
	public void log(String action, String details) {
		String username = resolveCurrentUsername();
		log("SYSTEM", UUID.randomUUID(), action, username, null, null, details);
	}

	/**
	 * Convenience method with entity type and action.
	 */
	@Transactional
	public void log(String entityType, UUID entityId, String action, String details) {
		String username = resolveCurrentUsername();
		log(entityType, entityId, action, username, null, null, details);
	}

	@Transactional(readOnly = true)
	public List<AuditLog> getAuditLogs(String entityType, UUID entityId) {
		return auditLogRepository.findByEntityTypeAndEntityIdOrderByPerformedAtDesc(entityType, entityId);
	}

	@Transactional(readOnly = true)
	public Page<AuditLog> getAuditLogsByUser(String username, Pageable pageable) {
		return auditLogRepository.findByPerformedByOrderByPerformedAtDesc(username, pageable);
	}

	private String resolveCurrentUsername() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
				return auth.getName();
			}
		} catch (Exception e) {
			// fall through
		}
		return "SYSTEM";
	}
}
