package com.aline.core.exception.gone;

import com.aline.core.exception.GoneException;

public class TokenExpiredException extends GoneException {
    public TokenExpiredException() {
        super("Token has expired or no longer exists.");
    }
}
