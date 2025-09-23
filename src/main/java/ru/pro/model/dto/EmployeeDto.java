package ru.pro.model.dto;

import jakarta.validation.constraints.Pattern;

public record EmployeeDto(
        String id,
        String firstName,
        String lastName,
        String position,
        @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Salary must be a number with max two decimal places")
        String salary,
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "ID must be a valid UUID format")
        String departmentId
) {
}
