package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.EmployeeEntity;
import ru.pro.projections.EmployeeProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    List<EmployeeProjection> findAllProjectedBy();

    Optional<EmployeeProjection> findProjectedById(UUID id);
}
