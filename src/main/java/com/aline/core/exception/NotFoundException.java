package com.aline.core.exception;

import com.aline.core.exception.handler.GlobalExceptionHandler;

/**
 * Super class for not found exceptions.
 * <p>Status Code: <code>404 NOT FOUND</code></p>
 * <p>
 *     <em>Extends <code>{@link RuntimeException}</code> to allow it to be caught by the {@link GlobalExceptionHandler}.</em>
 * </p>
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
