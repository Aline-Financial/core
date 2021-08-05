package com.aline.core.exception.notfound;

import com.aline.core.exception.NotFoundException;

public class UsernameNotFoundException extends NotFoundException {
    public UsernameNotFoundException(String s) {super(String.format("Username %s does not exist.", s));}
}
