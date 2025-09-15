package ru.pro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.BookEntity;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    @EntityGraph(attributePaths = "authors")
    Page<BookEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "authors")
    Optional<BookEntity> findById(UUID id);
}
