package ru.pro.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderDto> findById(@Valid UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Override
    public ResponseEntity<OrderDto> create(@Valid OrderDto dto) {
        OrderDto created = orderService.create(dto);
        return ResponseEntity.status(CREATED).body(created);
    }
}
