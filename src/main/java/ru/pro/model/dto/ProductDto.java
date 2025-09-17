package ru.pro.model.dto;

import java.util.UUID;

public record ProductDto(UUID id, String name, String description, String price, Integer quantityStock) {
}
