package ru.pro.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OrderItemDto(
        @NotNull(message = "OrderItem id is mandatory")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String id,
        @NotNull(message = "OrderId is mandatory")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String orderId,
        @NotNull(message = "ProductId is mandatory")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String productId,
        @NotNull(message = "Quantity is mandatory")
        @Min(value = 1, message = "Quantity must be >= 1")
        Integer quantity,
        @NotBlank(message = "Price is mandatory")
        String price) {
}
