package ru.pro.model.dto;

import ru.pro.model.enums.Genre;
import ru.pro.model.enums.Language;

import java.time.Year;
import java.util.UUID;

public record BookDto(
        UUID id,
        String title,
        Year publishedDate,
        Genre genre,
        Language language
) {}
