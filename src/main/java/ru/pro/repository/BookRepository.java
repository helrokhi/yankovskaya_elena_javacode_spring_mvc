package ru.pro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.pro.model.entity.BookEntity;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    @NonNull
    Page<BookEntity> findAll(@NonNull Pageable pageable);

    @NonNull
    Optional<BookEntity> findById(@NonNull UUID id);
}
