package com.aline.core.exception.forbidden;

import com.aline.core.exception.ForbiddenException;

/**
 * Used for the authentication of a One-Time Passcode.
 */
public class IncorrectOTPException extends ForbiddenException {
    public IncorrectOTPException() {
        super("One-time passcode is not correct.");
    }
}
