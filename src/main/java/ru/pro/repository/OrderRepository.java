package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(UUID id);
}
