package ru.pro.model.dto;

import ru.pro.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDto(UUID id, String name, Integer items, BigDecimal amount, OrderStatus status) {
}
