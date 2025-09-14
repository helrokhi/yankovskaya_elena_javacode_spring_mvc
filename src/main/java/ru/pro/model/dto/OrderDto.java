package ru.pro.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import ru.pro.model.enums.OrderStatus;
import ru.pro.views.Views;

import java.math.BigDecimal;
import java.util.UUID;

@JsonView(Views.UserDetails.class)
public record OrderDto(UUID id, String name, Integer items, BigDecimal amount, OrderStatus status) {
}
