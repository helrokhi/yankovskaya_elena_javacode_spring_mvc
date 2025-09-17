package ru.pro.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record OrderDto(UUID id, UUID customerId, UUID productId, LocalDate orderDate, String shippingAddress,
                       String totalPrice, String orderStatus) {
}
