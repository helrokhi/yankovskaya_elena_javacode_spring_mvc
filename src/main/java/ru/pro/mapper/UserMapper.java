package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "orders", ignore = true)
    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> entities);
}
