package com.aline.core.exception.unprocessable;

import com.aline.core.exception.ForbiddenException;
import com.aline.core.exception.UnauthorizedException;
import com.aline.core.exception.UnprocessableException;

/**
 * Used for the authentication of a One-Time Passcode.
 */
public class IncorrectOTPException extends UnprocessableException {
    public IncorrectOTPException() {
        super("One-time passcode is not correct.");
    }
}
