package com.aline.core.exception;

/**
 * 403 Unauthorized
 */
public class UnauthorizedException extends ResponseEntityException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
