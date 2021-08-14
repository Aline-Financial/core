package com.aline.core.exception;

/**
 * Abstract ResponseEntityException that is extended to be caught
 * by the {@link com.aline.core.exception.handler.GlobalExceptionHandler}.
 */
public abstract class ResponseEntityException extends RuntimeException {
    public ResponseEntityException(String message) {
        super(message);
    }
}
