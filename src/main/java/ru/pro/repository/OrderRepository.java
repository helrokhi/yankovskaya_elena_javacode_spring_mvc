package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
