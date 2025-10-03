package ru.pro.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update");

    private final String permission;
}

