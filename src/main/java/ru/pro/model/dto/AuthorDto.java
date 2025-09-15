package ru.pro.model.dto;

import ru.pro.model.enums.Country;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDto(
        UUID id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Country country
) {}
