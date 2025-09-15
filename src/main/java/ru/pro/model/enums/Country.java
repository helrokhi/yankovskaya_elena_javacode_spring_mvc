package ru.pro.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Country {
    RU("Россия"),
    US("США"),
    GB("Великобритания");

    private final String displayName;
}
