package ru.pro.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language {
    RUSSIAN("Русский", "RU"),
    ENGLISH("Английский", "EN"),
    FRENCH("Французский", "FR"),
    GERMAN("Немецкий", "DE"),
    SPANISH("Испанский", "ES"),
    ITALIAN("Итальянский", "IT"),
    CHINESE("Китайский", "ZH"),
    JAPANESE("Японский", "JA"),
    KOREAN("Корейский", "KO"),
    ARABIC("Арабский", "AR");

    private final String displayName;
    private final String code;
}
