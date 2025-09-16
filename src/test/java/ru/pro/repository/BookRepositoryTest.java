package ru.pro.repository;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.pro.model.entity.AuthorEntity;
import ru.pro.model.entity.BookEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("findById возвращает книгу по ID")
    void testFindById() {
        BookEntity entity = bookRepository.findAll().stream()
                .filter(b -> b.getTitle().equals("1984"))
                .findFirst()
                .orElseThrow();

        Optional<BookEntity> found = bookRepository.findById(entity.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("1984");
    }

    @Test
    @DisplayName("findById для несуществующего UUID возвращает пустой Optional")
    void testFindByIdNotFound() {
        Optional<BookEntity> found = bookRepository.findById(UUID.randomUUID());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("save сохраняет новую книгу")
    void testSave() {
        BookEntity newBook = new BookEntity(null, "Charlie", Set.of());
        BookEntity saved = bookRepository.save(newBook);

        assertThat(saved.getId()).isNotNull();
        assertThat(bookRepository.findById(saved.getId())).isPresent();
    }

    @Test
    @DisplayName("deleteById удаляет книгу")
    void testDeleteById() {
        BookEntity book = bookRepository.findAll().stream()
                .filter(b -> b.getTitle().equals("Animal Farm"))
                .findFirst()
                .orElseThrow();

        bookRepository.deleteById(book.getId());
        assertThat(bookRepository.findById(book.getId())).isEmpty();
    }

    @Test
    @DisplayName("saveAndFlush выбрасывает исключение при null title")
    void testSaveBookWithNullTitleThrowsException() {
        BookEntity book = new BookEntity(null, null, Set.of());

        assertThatThrownBy(() -> bookRepository.saveAndFlush(book))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("saveAndFlush выбрасывает исключение при пустом title")
    void testSaveBookWithBlankTitleThrowsException() {
        BookEntity book = new BookEntity(null, "   ", Set.of());

        assertThatThrownBy(() -> bookRepository.saveAndFlush(book))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("findAll с pageable возвращает корректную страницу")
    void testFindAllWithPageable() {
        Page<BookEntity> page = bookRepository.findAll(PageRequest.of(0, 5));
        assertThat(page).isNotNull();
        assertThat(page.getContent()).hasSize(5);
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(10);
    }

    @Test
    @DisplayName("сохраняется связь книга ↔ авторы")
    void testSaveBookWithAuthors() {
        AuthorEntity author = new AuthorEntity(null, "Mark", "Twain", Set.of());
        authorRepository.save(author);

        BookEntity book = new BookEntity(null, "Tom Sawyer", Set.of(author));
        BookEntity saved = bookRepository.save(book);

        entityManager.flush();
        entityManager.clear();

        BookEntity reloaded = bookRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getAuthors()).hasSize(1);
        assertThat(reloaded.getAuthors().iterator().next().getLastName()).isEqualTo("Twain");
    }

    @Test
    @DisplayName("удаление всех записей")
    void testDeleteAll() {
        bookRepository.deleteAll();
        assertThat(bookRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("save сохраняет несколько книг")
    void testSaveMultipleBooks() {
        BookEntity book1 = new BookEntity(null, "Book1", Set.of());
        BookEntity book2 = new BookEntity(null, "Book2", Set.of());

        bookRepository.saveAll(List.of(book1, book2));

        assertThat(bookRepository.findAll())
                .extracting(BookEntity::getTitle)
                .contains("Book1", "Book2");
    }
}