package ru.pro.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.AuthLogApi;
import ru.pro.model.dto.AuthLogDto;
import ru.pro.service.AuthLogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthLogController implements AuthLogApi {
    private final AuthLogService authLogService;

    @Override
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<AuthLogDto>> findAll() {
        return ResponseEntity.ok(authLogService.findAll());
    }
}
