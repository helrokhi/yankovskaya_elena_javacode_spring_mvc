package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.entity.Order;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, ProductMapper.class})
public interface OrderMapper {
    @Mapping(target = "customerId", source = "customer.id")
    OrderDto toDto(Order order);

    @Mapping(target = "customer.id", source = "customerId")
    Order toEntity(OrderDto dto);
}
