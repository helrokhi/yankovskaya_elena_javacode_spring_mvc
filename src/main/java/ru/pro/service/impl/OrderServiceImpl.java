package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.mapper.OrderItemMapper;
import ru.pro.mapper.OrderMapper;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.dto.OrderItemDto;
import ru.pro.model.entity.Order;
import ru.pro.model.entity.OrderItem;
import ru.pro.model.entity.Product;
import ru.pro.repository.CustomerRepository;
import ru.pro.repository.OrderItemRepository;
import ru.pro.repository.OrderRepository;
import ru.pro.repository.ProductRepository;
import ru.pro.service.OrderService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;


    @Override
    public OrderDto findById(UUID id) {
        Order entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        Set<OrderItem> items = orderItemRepository.findByOrderId(entity.getId());

        return orderMapper.toDto(entity, orderItemMapper.toDtoSet(items));
    }

    @Override
    @Transactional
    public OrderDto create(OrderDto dto) {
        UUID customerId = UUID.fromString(dto.customerId());
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }

        Order order = orderMapper.toEntity(dto);

        BigDecimal totalPrice = dto.items().stream()
                .map(item -> new BigDecimal(item.price())
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);
        Set<OrderItem> items = new HashSet<>();
        Order saved = orderRepository.save(order);
        for (OrderItemDto item : dto.items()) {
            Product product = productRepository.findById(UUID.fromString(item.productId()))
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + item.productId()));

            if (product.getQuantityStock() < item.quantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            product.setQuantityStock(product.getQuantityStock() - item.quantity());

            OrderItem orderItem = orderItemMapper.toEntity(item);
            orderItem.setOrder(order);
            OrderItem save = orderItemRepository.save(orderItem);
            items.add(save);
            productRepository.save(product);
        }
        return orderMapper.toDto(saved, orderItemMapper.toDtoSet(items));
    }
}
