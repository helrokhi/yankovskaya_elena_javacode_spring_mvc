package ru.pro.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(
        @NotNull(message = "Order id is mandatory")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String id,
        @NotNull(message = "CustomerId is mandatory")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String customerId,
        @NotEmpty(message = "Order items cannot be empty") Set<@Valid OrderItemDto> items,
        @NotBlank(message = "Shipping address is mandatory") String shippingAddress,
        String totalPrice,
        String orderStatus,
        LocalDateTime orderDate) {
}
