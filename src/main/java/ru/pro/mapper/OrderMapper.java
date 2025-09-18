package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.dto.OrderItemDto;
import ru.pro.model.entity.Order;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, ProductMapper.class})
public interface OrderMapper {
    @Mapping(target = "customerId", source = "order.customer.id")
    @Mapping(target = "items", source = "items")
    OrderDto toDto(Order order, Set<OrderItemDto> items);

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "totalPrice", ignore = true)
    Order toEntity(OrderDto dto);
}
