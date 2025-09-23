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
import ru.pro.model.entity.EmployeeEntity;
import ru.pro.repository.DepartmentRepository;
import ru.pro.repository.EmployeeRepository;

import java.math.BigDecimal;

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
class EmployeeApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private EmployeeEntity savedEmployee;
    private DepartmentEntity savedDepartment;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        savedDepartment = departmentRepository.save(new DepartmentEntity(null, "IT"));

        savedEmployee = employeeRepository.save(new EmployeeEntity(
                null,
                "John",
                "Doe",
                "Developer",
                new BigDecimal("1500.50"),
                savedDepartment
        ));
    }

    @Test
    @DisplayName("GET /api/v1/employees — список сотрудников")
    void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/v1/employees")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].position").value("Developer"))
                .andExpect(jsonPath("$[0].department.name").value("IT"));
    }

    @Test
    @DisplayName("GET /api/v1/employees/{id} — найден")
    void testGetEmployeeById_Valid() throws Exception {
        mockMvc.perform(get("/api/v1/employees/{id}", savedEmployee.getId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.position").value("Developer"))
                .andExpect(jsonPath("$.department.name").value("IT"));
    }

    @Test
    @DisplayName("POST /api/v1/employees — создать корректно")
    void testCreateEmployee_Valid() throws Exception {
        String json = """
                {
                  "firstName": "Alice",
                  "lastName": "Smith",
                  "position": "Manager",
                  "salary": "2500.00",
                  "departmentId": "%s"
                }
                """.formatted(savedDepartment.getId());

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.departmentId").value(savedDepartment.getId().toString()));
    }

    @Test
    @DisplayName("POST /api/v1/employees — ошибки валидации")
    void testCreateEmployee_Invalid() throws Exception {
        String json = """
                {
                  "firstName": "",
                  "lastName": "",
                  "position": "",
                  "salary": "abc",
                  "departmentId": "123"
                }
                """;

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST_FORMAT"));
    }

    @Test
    @DisplayName("PUT /api/v1/employees/{id} — обновить корректно")
    void testUpdateEmployee_Valid() throws Exception {
        String json = """
                {
                  "id": "%s",
                  "firstName": "Updated",
                  "lastName": "Name",
                  "position": "Lead",
                  "salary": "3000.00",
                  "departmentId": "%s"
                }
                """.formatted(savedEmployee.getId(), savedDepartment.getId());

        mockMvc.perform(put("/api/v1/employees/{id}", savedEmployee.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.salary").value("3000.00"))
                .andExpect(jsonPath("$.departmentId").value(savedDepartment.getId().toString()));
    }

    @Test
    @DisplayName("DELETE /api/v1/employees/{id} — удалить корректно")
    void testDeleteEmployee_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/{id}", savedEmployee.getId()))
                .andExpect(status().isNoContent());
    }
}