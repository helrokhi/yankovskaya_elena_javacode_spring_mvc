package ru.pro.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.pro.model.enums.Permission.ORDER_CREATE;
import static ru.pro.model.enums.Permission.ORDER_DELETE;
import static ru.pro.model.enums.Permission.ORDER_READ_ALL;
import static ru.pro.model.enums.Permission.ORDER_READ_OWN;
import static ru.pro.model.enums.Permission.ORDER_UPDATE;
import static ru.pro.model.enums.Permission.PRODUCT_CREATE;
import static ru.pro.model.enums.Permission.PRODUCT_DELETE;
import static ru.pro.model.enums.Permission.PRODUCT_READ;
import static ru.pro.model.enums.Permission.PRODUCT_UPDATE;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(Set.of(
            PRODUCT_READ,
            ORDER_CREATE,
            ORDER_READ_OWN
    )),

    MODERATOR(Set.of(
            PRODUCT_READ,
            PRODUCT_CREATE,
            PRODUCT_UPDATE,
            ORDER_READ_ALL,
            ORDER_UPDATE
    )),

    SUPER_ADMIN(Set.of(
            PRODUCT_READ,
            PRODUCT_CREATE,
            PRODUCT_UPDATE,
            PRODUCT_DELETE,
            ORDER_CREATE,
            ORDER_READ_OWN,
            ORDER_READ_ALL,
            ORDER_UPDATE,
            ORDER_DELETE
    ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
