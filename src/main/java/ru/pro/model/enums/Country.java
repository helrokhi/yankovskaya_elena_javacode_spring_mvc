package ru.pro.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Country {
    RUSSIA("Россия", "RU"),
    USA("США", "US"),
    FRANCE("Франция", "FR"),
    GERMANY("Германия", "DE"),
    SPAIN("Испания", "ES"),
    ITALY("Италия", "IT"),
    CHINA("Китай", "CN"),
    JAPAN("Япония", "JP"),
    SOUTH_KOREA("Южная Корея", "KR"),
    UK("Великобритания", "GB"),
    CANADA("Канада", "CA"),
    BRAZIL("Бразилия", "BR"),
    INDIA("Индия", "IN"),
    AUSTRALIA("Австралия", "AU");

    private final String displayName;
    private final String code;
}
