package ru.pro.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Genre {
    FANTASY("Фэнтези"),
    DRAMA("Драма"),
    DETECTIVE("Детектив"),
    NOVEL("Новелла"),
    ROMANCE("Роман"),
    ADVENTURE("Приключения"),
    DYSTOPIA("Антиутопия"),
    HISTORICAL("История"),
    SATIRE("Сатира"),
    PHILOSOPHY("Философия");
    private final String displayName;
}
