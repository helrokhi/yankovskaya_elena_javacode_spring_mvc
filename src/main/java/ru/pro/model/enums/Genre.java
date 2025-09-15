package ru.pro.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Genre {
    FANTASY("Фэнтези"),
    SCIENCE_FICTION("Научная фантастика"),
    MYSTERY("Детектив"),
    THRILLER("Триллер"),
    ROMANCE("Роман"),
    HORROR("Ужасы"),
    BIOGRAPHY("Биография"),
    HISTORY("История"),
    POETRY("Поэзия"),
    CHILDREN("Детская литература");
    private final String displayName;
}
