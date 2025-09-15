package ru.pro.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import ru.pro.views.Views;

import java.util.List;
import java.util.UUID;

@JsonView(Views.UserSummary.class)
public record UserDto(
        UUID id,
        String name,
        @Email String email,
        @JsonView(Views.UserDetails.class) List<OrderDto> orders) {
}
