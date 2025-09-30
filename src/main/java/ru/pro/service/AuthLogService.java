package ru.pro.service;

import ru.pro.model.dto.AuthLogDto;

import java.util.List;

public interface AuthLogService {
    void log(AuthLogDto dto);

    List<AuthLogDto> findAll();
}
