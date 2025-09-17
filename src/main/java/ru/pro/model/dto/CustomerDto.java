package ru.pro.model.dto;

import java.util.UUID;

public record CustomerDto(UUID id, String firstName, String lastName, String email, String contactNumber) {
}
