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
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import ru.pro.api.controller.UserController;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.dto.UserDto;
import ru.pro.model.enums.OrderStatus;
import ru.pro.model.response.PagedResponse;
import ru.pro.service.UserService;

import java.math.BigDecimal;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        PagedResponse<UserDto> pagedResponse = new PagedResponse<>(
                List.of(user),
                1,
                1,
                0,
                5
        );

        when(userService.findAll(any(Pageable.class)))
                .thenReturn(pagedResponse);

        mockMvc.perform(get("/api/v1/users")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(user.id().toString()))
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"))

                .andExpect(jsonPath("$.content[0].orders").doesNotExist())

                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(5));
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

    @Test
    @DisplayName("GET /api/v1/users — JSON View UserSummary (без orders)")
    void testGetAllUsersJsonView() throws Exception {
        UserDto user = new UserDto(
                UUID.randomUUID(),
                "Alice",
                "alice@example.com",
                List.of(new OrderDto(UUID.randomUUID(), "Order1", 2, BigDecimal.TEN, OrderStatus.NEW))
        );

        PagedResponse<UserDto> pagedResponse = new PagedResponse<>(
                List.of(user),   // content
                1,               // totalElements
                1,               // totalPages
                0,               // number (page)
                5                // size
        );

        when(userService.findAll(any(Pageable.class)))
                .thenReturn(pagedResponse);

        mockMvc.perform(get("/api/v1/users")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(user.id().toString()))
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"))
                // orders НЕ должен сериализоваться в UserSummary
                .andExpect(jsonPath("$.content[0].orders").doesNotExist())
                // дополнительные проверки страницы
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(5));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} — JSON View UserDetails (включает orders)")
    void testGetUserByIdJsonView() throws Exception {
        UUID id = UUID.randomUUID();
        OrderDto order = new OrderDto(UUID.randomUUID(), "Order1", 2, BigDecimal.TEN, OrderStatus.NEW);
        UserDto user = new UserDto(id, "Bob", "bob@example.com", List.of(order));

        when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", id)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                // должны быть id, name, email
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.email").value("bob@example.com"))
                // orders должен присутствовать
                .andExpect(jsonPath("$.orders[0].id").value(order.id().toString()))
                .andExpect(jsonPath("$.orders[0].name").value("Order1"))
                .andExpect(jsonPath("$.orders[0].items").value(2))
                .andExpect(jsonPath("$.orders[0].amount").value(10))
                .andExpect(jsonPath("$.orders[0].status").value("NEW"));
    }
}