package com.aline.core.exception.notcreated;

import com.aline.core.exception.NotCreatedException;

public class ApplicationNotCreatedException extends NotCreatedException {
    public ApplicationNotCreatedException() {
        super("Application could not be processed.");
    }
}
