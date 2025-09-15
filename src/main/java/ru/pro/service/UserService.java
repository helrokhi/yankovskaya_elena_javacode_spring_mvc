package ru.pro.service;

import org.springframework.data.domain.Pageable;
import ru.pro.model.dto.UserDto;
import ru.pro.model.response.PagedResponse;

import java.util.UUID;

public interface UserService {
    PagedResponse<UserDto> findAll(Pageable pageable);

    UserDto findById(UUID id);

    UserDto create(UserDto dto);

    UserDto update(UUID id, UserDto dto);

    void delete(UUID id);
}
