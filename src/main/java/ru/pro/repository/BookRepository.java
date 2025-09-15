package ru.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pro.model.entity.BookEntity;

import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
}
