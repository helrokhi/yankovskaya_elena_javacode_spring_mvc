package ru.pro.model.dto;

import java.util.List;
import java.util.UUID;

public record UserDto(UUID id, String name, String email, List<OrderDto> orders) {
}
