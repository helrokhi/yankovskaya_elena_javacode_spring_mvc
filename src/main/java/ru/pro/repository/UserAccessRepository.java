package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.UserAccess;

import java.util.Optional;
import java.util.UUID;

public interface UserAccessRepository extends JpaRepository<UserAccess, UUID> {
    Optional<UserAccess> findByLogin(String login);
}
