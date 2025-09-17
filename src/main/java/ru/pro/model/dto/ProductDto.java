package ru.pro.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProductDto(
        @NotNull(message = "Id is mandatory")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String id,
        @NotBlank(message = "Name is mandatory") String name,
        String description,

        @NotNull(message = "Price is mandatory")
        @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
        String price,

        @NotNull(message = "Quantity is mandatory")
        @Min(value = 0, message = "Quantity must be >= 0")
        Integer quantityStock) {
}
