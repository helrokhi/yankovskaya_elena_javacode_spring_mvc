package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.OrderItemDto;
import ru.pro.model.entity.OrderItem;

import java.util.Set;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderItemMapper {
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    OrderItemDto toDto(OrderItem entity);

    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "product.id", source = "productId")
    OrderItem toEntity(OrderItemDto dto);

    Set<OrderItemDto> toDtoSet(Set<OrderItem> entities);

    Set<OrderItem> toEntitySet(Set<OrderItemDto> dtos);
}
