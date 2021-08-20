package com.aline.core.exception;

/**
 * 403 Unauthorized
 */
public class ForbiddenException extends ResponseEntityException {
    public ForbiddenException(String message) {
        super(message);
    }
}
