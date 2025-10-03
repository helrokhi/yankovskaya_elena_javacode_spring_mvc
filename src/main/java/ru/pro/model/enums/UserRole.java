package ru.pro.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.pro.model.enums.Permission.CUSTOMER_READ;
import static ru.pro.model.enums.Permission.CUSTOMER_UPDATE;
import static ru.pro.model.enums.Permission.USER_READ;
import static ru.pro.model.enums.Permission.USER_UPDATE;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(Set.of(
            USER_READ,
            CUSTOMER_READ
    )),
    ADMIN(Set.of(
            USER_READ,
            USER_UPDATE,
            CUSTOMER_READ,
            CUSTOMER_UPDATE
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
