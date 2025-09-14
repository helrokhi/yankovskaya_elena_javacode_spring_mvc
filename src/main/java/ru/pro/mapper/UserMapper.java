package ru.pro.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.UserEntity;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "orders", ignore = true)
    UserDto toDto(UserEntity entity);

    UserDto userDtoToUserEntity(UserEntity entity, List<OrderDto> orders);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(UserDto source, @MappingTarget UserEntity target);
}
