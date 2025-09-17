package ru.pro.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.OrderApi;
import ru.pro.model.dto.OrderDto;
import ru.pro.service.OrderService;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    @PreAuthorize("hasAuthority('order:read:all') or hasAuthority('order:read:own')")
    public ResponseEntity<OrderDto> findById(@PathVariable UUID id, Authentication authentication) {
        return ResponseEntity.ok(orderService.findById(id, authentication));
    }

    @Override
    @PreAuthorize("hasAuthority('order:create')")
    public ResponseEntity<OrderDto> create(@Valid OrderDto dto, Authentication authentication) {
        OrderDto created = orderService.create(dto, authentication);
        return ResponseEntity.status(CREATED).body(created);
    }
}
