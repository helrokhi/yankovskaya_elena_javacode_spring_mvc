package ru.pro.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.pro.model.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Book> rowMapper = new BeanPropertyRowMapper<>(Book.class);

    public List<Book> findAll() {
        String sql = "SELECT id, title, author, publication_year FROM books";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Book> findById(UUID id) {
        String sql = "SELECT id, title, author, publication_year FROM books WHERE id = ?";
        try {
            Book book = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void insert(Book book) {
        String sql = "INSERT INTO books (id, title, author, publication_year) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getId(), book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM books WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    public boolean existsById(UUID id) {
        String sql = "SELECT COUNT(*) FROM books WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
