package ru.pro.model.dto;

import java.util.UUID;

public record AuthorDto(
        UUID id,
        String firstName,
        String lastName
) {
}
