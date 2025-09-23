package ru.pro.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmployeeDto(
        String id,
        @NotBlank(message = "FirstName cannot be blank", groups = OnCreate.class)
        String firstName,
        @NotBlank(message = "LastName cannot be blank", groups = OnCreate.class)
        String lastName,
        @NotBlank(message = "Position cannot be blank", groups = OnCreate.class)
        String position,
        @Pattern(
                regexp = "\\d+(\\.\\d{1,2})?",
                message = "Salary must be a number with max two decimal places",
                groups = {OnCreate.class, OnUpdate.class})
        String salary,
        @NotBlank(message = "DepartmentId cannot be blank", groups = OnCreate.class)
        @Pattern(
                regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "DepartmentId must be a valid UUID format",
                groups = {OnCreate.class, OnUpdate.class}       )
        String departmentId
) {
        public interface OnCreate {}
        public interface OnUpdate {}
}
