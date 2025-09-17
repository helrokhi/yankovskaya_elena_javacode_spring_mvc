package ru.pro.model.dto;

import java.util.UUID;

public record OrderItemDto(UUID id, UUID orderId, UUID productId, Integer quantity, String price) {
}
