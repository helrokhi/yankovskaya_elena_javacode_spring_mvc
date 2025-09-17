package ru.pro.model.dto;

public record AuthLogDto(String login, String status, String action, String details) {
}
