package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.OrderItem;

import java.util.Set;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    Set<OrderItem> findByOrderId(UUID orderId);
}
