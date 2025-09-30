package ru.pro.model.dto;

import jakarta.validation.constraints.Email;

public record UserDto(@Email String login, String password, String role) {
}
