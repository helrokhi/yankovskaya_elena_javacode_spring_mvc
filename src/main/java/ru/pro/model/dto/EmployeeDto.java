package ru.pro.model.dto;

public record EmployeeDto(
        String id,
        String firstName,
        String lastName,
        String position,
        String salary,
        String departmentId
) {
}
