package com.aline.core.exception;

public class NotCreatedException extends ResponseEntityException {
    public NotCreatedException(String message) {
        super(message);
    }
}
