package ru.pro.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    PRODUCT_READ("product:read"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    ORDER_CREATE("order:create"),
    ORDER_READ_OWN("order:read:own"),
    ORDER_READ_ALL("order:read:all"),
    ORDER_UPDATE("order:update"),
    ORDER_DELETE("order:delete");

    private final String permission;
}

