package ru.pro.service;

import org.springframework.security.core.Authentication;
import ru.pro.model.dto.OrderDto;

import java.util.UUID;

public interface OrderService {
    OrderDto findById(UUID id, Authentication authentication);

    OrderDto create(OrderDto orderDto, Authentication authentication);
}
