package ru.pro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.pro.api.controller.UserController;
import ru.pro.model.dto.UserDto;
import ru.pro.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserApiTest.TestConfig.class)
class UserApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }
    }

    @Test
    @DisplayName("GET /api/v1/users — вернуть список пользователей")
    void testGetAllUsers() throws Exception {
        UserDto user = new UserDto(UUID.randomUUID(), "Alice", "alice@example.com", List.of());

        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/users")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(user))));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} — вернуть пользователя по ID")
    void testGetUserById() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto user = new UserDto(id, "Bob", "bob@example.com", List.of());

        when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", id)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    @DisplayName("POST /api/v1/users — создать нового пользователя")
    void testCreateUser() throws Exception {
        UserDto input = new UserDto(null, "Charlie", "charlie@example.com", List.of());
        UserDto saved = new UserDto(UUID.randomUUID(), "Charlie", "charlie@example.com", List.of());

        when(userService.create(any(UserDto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(saved)));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} — обновить пользователя")
    void testUpdateUser() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto input = new UserDto(id, "David", "david@example.com", List.of());
        UserDto updated = new UserDto(id, "David", "david@example.com", List.of());

        when(userService.update(eq(id), any(UserDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} — удалить пользователя")
    void testDeleteUser() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(userService).delete(id);
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} — 404 если пользователь не найден")
    void testUserNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.findById(id))
                .thenThrow(new EntityNotFoundException("User not found: " + id));

        mockMvc.perform(get("/api/v1/users/{id}", id)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}