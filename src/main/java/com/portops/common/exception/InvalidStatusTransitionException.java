package com.portops.common.exception;

/**
 * Exception thrown when an invalid status transition is attempted on an entity.
 */
public class InvalidStatusTransitionException extends RuntimeException {
	public InvalidStatusTransitionException(String message) {
		super(message);
	}
}
