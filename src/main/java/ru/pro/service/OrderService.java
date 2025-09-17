package ru.pro.service;

import ru.pro.model.dto.OrderDto;

import java.util.UUID;

public interface OrderService {
    OrderDto findById(UUID id);

    OrderDto create(OrderDto orderDto);
}
