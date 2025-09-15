package ru.pro.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language {
    RU("Русский"),
    EN("Английский");

    private final String displayName;
}
