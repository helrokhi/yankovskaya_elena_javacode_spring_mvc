package ru.pro.mapper;

import org.mapstruct.Mapper;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.entity.OrderEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(OrderEntity order);

    OrderEntity toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<OrderEntity> entities);
}
