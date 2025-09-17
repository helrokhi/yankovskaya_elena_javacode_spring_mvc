package ru.pro.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProductDto(
        String id,

        @NotBlank(message = "Name is mandatory")
        String name,
        String description,

        @NotBlank(message = "Price cannot be blank")
        @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Price must be a number with max two decimal places")
        String price,

        @NotNull(message = "Quantity is mandatory")
        @Min(value = 0, message = "Quantity must be >= 0")
        Integer quantityStock) {
}
