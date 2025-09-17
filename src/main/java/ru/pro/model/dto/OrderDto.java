package ru.pro.model.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record OrderDto(UUID id, UUID customerId, Set<OrderItemDto> items, String shippingAddress, String totalPrice,
                       String orderStatus,
                       LocalDateTime orderDate) {
}
