package com.aline.core.model;

public enum UserRole {
    MEMBER("member"),
    EMPLOYEE("employee"),
    ADMINISTRATOR("administrator");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
