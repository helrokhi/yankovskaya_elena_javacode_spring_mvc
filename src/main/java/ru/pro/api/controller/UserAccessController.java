package ru.pro.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.UserAccessApi;
import ru.pro.service.UserAccessService;

@RestController
@RequiredArgsConstructor
public class UserAccessController implements UserAccessApi {
    private final UserAccessService userAccessService;

    @Override
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<Void> unlockAccount(@PathVariable String login) {
        userAccessService.unlockAccount(login);
        return ResponseEntity.ok().build();
    }
}
