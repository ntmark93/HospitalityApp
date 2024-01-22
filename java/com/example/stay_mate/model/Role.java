package com.example.stay_mate.model;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");
    final java.lang.String roleCode;

    Role(java.lang.String roleCode) {
        this.roleCode=roleCode;
    }
}
