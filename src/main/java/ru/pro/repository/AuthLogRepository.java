package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.AuthLogEntity;

import java.util.UUID;

public interface AuthLogRepository extends JpaRepository<AuthLogEntity, UUID> {
}
