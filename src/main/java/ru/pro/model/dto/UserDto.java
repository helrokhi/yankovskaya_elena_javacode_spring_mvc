package ru.pro.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import ru.pro.views.Views;

import java.util.List;
import java.util.UUID;

public record UserDto(
        @JsonView(Views.UserSummary.class) UUID id,
        @JsonView(Views.UserSummary.class) String name,
        @Email
        @JsonView(Views.UserSummary.class) String email,
        @JsonView(Views.UserDetails.class) List<OrderDto> orders) {
}
