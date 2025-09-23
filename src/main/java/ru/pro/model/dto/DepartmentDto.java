package ru.pro.model.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentDto(
        String id,
        @NotBlank(message = "Name cannot be blank")
        String name
) {
}
