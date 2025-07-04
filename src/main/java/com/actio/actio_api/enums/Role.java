package com.actio.actio_api.enums;

/**
 * Enumeration representing user roles within the application.
 *
 * Each role defines a specific level of access or permissions.
 * This enum is typically used for role-based authorization and access control.
 */
public enum Role {

    /**
     * Administrator role with elevated permissions.
     */
    ADMIN("admin"),
    /**
     * Client role with standard user access.
     */
    CLIENT("client");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
