package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.entity.OrderEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(OrderEntity order);

    @Mapping(target = "user", ignore = true)
    OrderEntity toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<OrderEntity> entities);
}
