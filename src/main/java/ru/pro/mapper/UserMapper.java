package ru.pro.mapper;

import org.mapstruct.Mapper;
import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.UserAccess;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserAccess entity);
}
