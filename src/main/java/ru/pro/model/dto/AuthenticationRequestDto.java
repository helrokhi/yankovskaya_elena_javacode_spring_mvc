package ru.pro.model.dto;

import jakarta.validation.constraints.Email;

public record AuthenticationRequestDto(@Email String login, String password) {
}
