package com.example.demo.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.user.Permission.*;

@RequiredArgsConstructor
public enum Role {
//no permisions for the user
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    PROPERTYOWNER_READ,
                    PROPERTYOWNER_UPDATE,
                    PROPERTYOWNER_DELETE,
                    PROPERTYOWNER_CREATE,
                    FINANCIALMANAGER_READ
            )
    ),
    PROPERTYOWNER(
            Set.of(
                    PROPERTYOWNER_READ,
                    PROPERTYOWNER_UPDATE,
                    PROPERTYOWNER_DELETE,
                    PROPERTYOWNER_CREATE
            )
    ),
    FINANCIALMANAGER(
            Set.of(
                    FINANCIALMANAGER_READ,
                    FINANCIALMANAGER_UPDATE,
                    FINANCIALMANAGER_DELETE,
                    FINANCIALMANAGER_CREATE
            )
    ),
    INSURANCEMANAGER(
            Set.of(
                    INSURANCEMANAGER_READ,
                    INSURANCEMANAGER_UPDATE,
                    INSURANCEMANAGER_DELETE,
                    INSURANCEMANAGER_CREATE
            )
    ),

    TASKSUPERVISOR(
            Set.of(
                    TASKSUPERVISOR_READ,
                    TASKSUPERVISOR_UPDATE,
                    TASKSUPERVISOR_DELETE,
                    TASKSUPERVISOR_CREATE
            )
    ),
    TOPMANAGER(
            Set.of(
                    TOPMANAGER_READ,
                    TOPMANAGER_UPDATE,
                    TOPMANAGER_DELETE,
                    TOPMANAGER_CREATE
            )
    ),
    MPC(
            Set.of(
                    MPC_READ,
                    MPC_UPDATE,
                    MPC_DELETE,
                    MPC_CREATE
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
