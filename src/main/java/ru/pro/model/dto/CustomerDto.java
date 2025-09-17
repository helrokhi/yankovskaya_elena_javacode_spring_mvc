package ru.pro.model.dto;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record CustomerDto(UUID id, String firstName, String lastName, @Email String email, String contactNumber) {
}
