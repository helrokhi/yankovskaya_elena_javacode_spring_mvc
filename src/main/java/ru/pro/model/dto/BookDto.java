package ru.pro.model.dto;

import java.util.Set;
import java.util.UUID;

public record BookDto(
        UUID id,
        String title,
        Set<AuthorDto> authors
) {
}
