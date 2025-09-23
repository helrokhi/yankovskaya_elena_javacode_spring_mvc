package ru.pro.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.pro.model.entity.DepartmentEntity;
import ru.pro.repository.DepartmentRepository;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartmentApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentEntity savedDepartment;

    @BeforeEach
    void setup() {
        departmentRepository.deleteAll();

        savedDepartment = departmentRepository.save(new DepartmentEntity(
                null,
                "IT"
        ));
    }

    @Test
    @DisplayName("GET /api/v1/departments — список департаментов")
    void testGetAllDepartments() throws Exception {
        mockMvc.perform(get("/api/v1/departments")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(savedDepartment.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("IT"));
    }

    @Test
    @DisplayName("GET /api/v1/departments/{id} — найден")
    void testGetDepartmentById_Valid() throws Exception {
        mockMvc.perform(get("/api/v1/departments/{id}", savedDepartment.getId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedDepartment.getId().toString()))
                .andExpect(jsonPath("$.name").value("IT"));
    }

    @Test
    @DisplayName("GET /api/v1/departments/{id} — не найден")
    void testGetDepartmentById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/departments/{id}", UUID.randomUUID().toString())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/departments — создать корректно")
    void testCreateDepartment_Valid() throws Exception {
        String json = """
                {
                  "name": "HR"
                }
                """;

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    @DisplayName("POST /api/v1/departments — ошибки валидации")
    void testCreateDepartment_Invalid() throws Exception {
        String json = """
                {
                  "name": ""
                }
                """;

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST_FORMAT"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("PUT /api/v1/departments/{id} — обновить корректно")
    void testUpdateDepartment_Valid() throws Exception {
        String json = """
                {
                  "id": "%s",
                  "name": "Finance"
                }
                """.formatted(savedDepartment.getId());

        mockMvc.perform(put("/api/v1/departments/{id}", savedDepartment.getId().toString())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedDepartment.getId().toString()))
                .andExpect(jsonPath("$.name").value("Finance"));
    }

    @Test
    @DisplayName("PUT /api/v1/departments/{id} — не найден")
    void testUpdateDepartment_NotFound() throws Exception {
        String json = """
                {
                  "id": "%s",
                  "name": "Marketing"
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(put("/api/v1/departments/{id}", UUID.randomUUID().toString())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/departments/{id} — удалить корректно")
    void testDeleteDepartment_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/departments/{id}", savedDepartment.getId().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/departments/{id} — не найден")
    void testDeleteDepartment_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/departments/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }
}