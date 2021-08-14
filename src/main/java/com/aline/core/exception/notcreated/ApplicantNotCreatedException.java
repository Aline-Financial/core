package com.aline.core.exception.notcreated;

import com.aline.core.exception.NotCreatedException;

public class ApplicantNotCreatedException extends NotCreatedException {
    public ApplicantNotCreatedException() {
        super("Applicant could not be processed.");
    }
}
