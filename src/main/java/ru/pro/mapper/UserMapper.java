package ru.pro.mapper;

import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.UserEntity;

import java.util.List;

public interface UserMapper {
    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> entities);
}
