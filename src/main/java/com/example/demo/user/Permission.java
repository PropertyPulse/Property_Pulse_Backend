package com.example.demo.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    PROPERTYOWNER_READ("propertyowner:read"),
    PROPERTYOWNER_UPDATE("propertyowner:update"),
    PROPERTYOWNER_CREATE("propertyowner:create"),
    PROPERTYOWNER_DELETE("propertyowner:delete"),

    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),

    FINANCIALMANAGER_READ("financialmanager:read"),
    FINANCIALMANAGER_UPDATE("financialmanager:update"),
    FINANCIALMANAGER_CREATE("financialmanager:create"),
    FINANCIALMANAGER_DELETE("financialmanager:delete"),

    INSURANCEMANAGER_READ("insurancemanager:read"),
    INSURANCEMANAGER_UPDATE("insurancemanager:update"),
    INSURANCEMANAGER_CREATE("insurancemanager:create"),
    INSURANCEMANAGER_DELETE("insurancemanager:delete"),

    TASKSUPERVISOR_READ("tasksupervisor:read"),
    TASKSUPERVISOR_UPDATE("tasksupervisor:update"),
    TASKSUPERVISOR_CREATE("tasksupervisor:create"),
    TASKSUPERVISOR_DELETE("tasksupervisor:delete"),

    TOPMANAGER_READ("topmanager:read"),
    TOPMANAGER_UPDATE("topmanager:update"),
    TOPMANAGER_CREATE("topmanager:create"),
    TOPMANAGER_DELETE("topmanager:delete"),

    MPC_READ("mpc:read"),
    MPC_UPDATE("mpc:update"),
    MPC_CREATE("mpc:create"),
    MPC_DELETE("mpc:delete"),









    ;





    @Getter
    private final String permission;/*permission name*/
}
