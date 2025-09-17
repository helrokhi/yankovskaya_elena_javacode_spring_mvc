package ru.pro.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.pro.model.entity.Product;
import ru.pro.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        savedProduct = productRepository.save(new Product(
                null,
                "Test Product",
                "Description",
                new BigDecimal("12.50"),
                100
        ));
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    void testGetProductById_Valid() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", savedProduct.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedProduct.getId().toString()))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.quantityStock").value(100));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/{id}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct_Valid() throws Exception {
        String json = """
                {
                  "name": "New Product",
                  "description": "New Desc",
                  "price": 5.00,
                  "quantityStock": 10
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                        .with(httpBasic("testUser", "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    void testCreateProduct_Invalid() throws Exception {
        String json = """
                {
                  "name": "",
                  "description": "Desc",
                  "price": -5,
                  "quantityStock": -1
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                        .with(httpBasic("testUser", "password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.name").exists())
                .andExpect(jsonPath("$.details.price").exists())
                .andExpect(jsonPath("$.details.quantityStock").exists());
    }

    @Test
    void testUpdateProduct_Valid() throws Exception {
        String json = """
                {
                  "name": "Updated Name",
                  "description": "Updated Desc",
                  "price": 20.00,
                  "quantityStock": 50
                }
                """;

        mockMvc.perform(put("/api/v1/products/{id}", savedProduct.getId())
                        .with(httpBasic("admin", "admin123")) // admin может обновлять
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.quantityStock").value(50));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        String json = """
                {
                  "name": "Updated Name",
                  "description": "Updated Desc",
                  "price": 20.00,
                  "quantityStock": 50
                }
                """;

        mockMvc.perform(put("/api/v1/products/{id}", UUID.randomUUID())
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{id}", savedProduct.getId())
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/products/{id}", randomId)
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ENTITY_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Product not found with id: " + randomId));
    }

    @Test
    void testUnauthorizedOnCreateProduct() throws Exception {
        String json = """
                {
                  "name": "Unauthorized",
                  "description": "Should fail",
                  "price": 10.00,
                  "quantityStock": 5
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }
}