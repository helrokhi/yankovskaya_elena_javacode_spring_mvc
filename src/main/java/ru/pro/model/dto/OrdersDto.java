package ru.pro.model.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record OrdersDto(UUID id, UUID customerId, Set<ProductDto> products, LocalDate orderDate, String shippingAddress,
                        String totalPrice, String orderStatus) {
}
