package com.samu.todoapi.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    } 
}
