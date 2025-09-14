package ru.pro.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.pro.config.DataInitializer;
import ru.pro.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer.run();
    }

    @Test
    void testFindAll() {
        List<UserEntity> users = userRepository.findAll();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isGreaterThanOrEqualTo(2);
        assertThat(users).extracting(UserEntity::getName)
                .containsExactlyInAnyOrder("Alice", "Bob");
    }

    @Test
    void testFindById() {
        UserEntity alice = userRepository.findAll().stream()
                .filter(u -> u.getName().equals("Alice"))
                .findFirst()
                .orElseThrow();

        Optional<UserEntity> found = userRepository.findById(alice.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void testSave() {
        UserEntity newUser = new UserEntity(null, "Charlie", "charlie@example.com");
        UserEntity saved = userRepository.save(newUser);

        assertThat(saved.getId()).isNotNull();
        assertThat(userRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void testDeleteById() {
        UserEntity alice = userRepository.findAll().stream()
                .filter(u -> u.getName().equals("Alice"))
                .findFirst()
                .orElseThrow();

        userRepository.deleteById(alice.getId());
        assertThat(userRepository.findById(alice.getId())).isEmpty();

        assertThat(userRepository.findAll())
                .extracting(UserEntity::getName)
                .doesNotContain("Alice");
    }

    @Test
    void testSaveUserWithOnFormatEmailThrowsException() {
        UserEntity user = new UserEntity(null, "TestUser", "fgh7tym");

        assertThatThrownBy(() -> userRepository.saveAndFlush(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testSaveUserWithNullEmailThrowsException() {
        UserEntity user = new UserEntity(null, "TestUser", null);

        assertThatThrownBy(() -> userRepository.saveAndFlush(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testSaveUserWithDuplicateEmailThrowsException() {
        UserEntity duplicate = new UserEntity(null, "DuplicateAlice", "alice@example.com");

        assertThatThrownBy(() -> userRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}