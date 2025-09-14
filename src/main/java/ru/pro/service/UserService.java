package ru.pro.service;

import ru.pro.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(UUID id);

    UserDto create(UserDto dto);

    UserDto update(UUID id, UserDto dto);

    void delete(UUID id);
}
