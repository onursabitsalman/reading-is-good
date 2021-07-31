package com.getir.readingisgood.entity.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
    ADMIN,
    CUSTOMER;

    public Set<SimpleGrantedAuthority> getPermissions() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return permissions;
    }

}
