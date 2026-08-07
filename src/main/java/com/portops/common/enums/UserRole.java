package com.portops.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the user roles within the Port Operations Management System.
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
	CUSTOMER("Customer", "ROLE_CUSTOMER"),
	OPERATOR("Operator", "ROLE_OPERATOR"),
	VERIFIER("Verifier", "ROLE_VERIFIER"),
	APPROVER("Approver", "ROLE_APPROVER"),
	FINANCE("Finance", "ROLE_FINANCE"),
	ADMIN("Admin", "ROLE_ADMIN"),
	VIEWER("Viewer", "ROLE_VIEWER");

	private final String displayName;
	private final String authority;
}
