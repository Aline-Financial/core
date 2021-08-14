package com.aline.core.exception.notfound;

import com.aline.core.exception.NotFoundException;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException() {
        super("Token is expired or no longer exists.");
    }
}
