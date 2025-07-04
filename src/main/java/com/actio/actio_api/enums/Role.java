package com.actio.actio_api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {

    ADMIN("admin"),
    CLIENT("client");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
