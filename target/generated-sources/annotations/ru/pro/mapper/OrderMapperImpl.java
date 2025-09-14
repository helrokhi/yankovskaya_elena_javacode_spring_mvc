package ru.pro.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.entity.OrderEntity;
import ru.pro.model.enums.OrderStatus;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-14T20:26:03+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (BellSoft)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(OrderEntity order) {
        if ( order == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        Integer items = null;
        BigDecimal amount = null;
        OrderStatus status = null;

        id = order.getId();
        name = order.getName();
        items = order.getItems();
        amount = order.getAmount();
        status = order.getStatus();

        OrderDto orderDto = new OrderDto( id, name, items, amount, status );

        return orderDto;
    }

    @Override
    public OrderEntity toEntity(OrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId( dto.id() );
        orderEntity.setName( dto.name() );
        orderEntity.setItems( dto.items() );
        orderEntity.setAmount( dto.amount() );
        orderEntity.setStatus( dto.status() );

        return orderEntity;
    }

    @Override
    public List<OrderDto> toDtoList(List<OrderEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( entities.size() );
        for ( OrderEntity orderEntity : entities ) {
            list.add( toDto( orderEntity ) );
        }

        return list;
    }
}
