package ru.pro.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(
        String id,
        @NotBlank(message = "Id cannot be blank")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String customerId,
        @NotEmpty(message = "Order items cannot be empty")
        Set<@Valid OrderItemDto> items,
        @NotBlank(message = "Shipping address is mandatory")
        String shippingAddress,
        String totalPrice,
        String orderStatus,
        LocalDateTime orderDate) {
}
