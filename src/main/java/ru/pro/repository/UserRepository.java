package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
