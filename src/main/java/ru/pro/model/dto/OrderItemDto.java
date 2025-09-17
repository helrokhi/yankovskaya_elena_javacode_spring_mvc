package ru.pro.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OrderItemDto(
        String id,
        String orderId,

        @NotBlank(message = "Id cannot be blank")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String productId,

        @NotNull(message = "Quantity is mandatory")
        @Min(value = 1, message = "Quantity must be >= 1")
        Integer quantity,

        @NotBlank(message = "Price cannot be blank")
        @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Price must be a number with max two decimal places")
        String price) {
}
