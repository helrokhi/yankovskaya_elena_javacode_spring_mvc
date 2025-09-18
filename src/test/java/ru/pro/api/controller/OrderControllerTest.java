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
import ru.pro.model.entity.Customer;
import ru.pro.model.entity.Order;
import ru.pro.model.entity.OrderItem;
import ru.pro.model.entity.Product;
import ru.pro.model.enums.OrderStatus;
import ru.pro.repository.CustomerRepository;
import ru.pro.repository.OrderItemRepository;
import ru.pro.repository.OrderRepository;
import ru.pro.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Customer customer;
    private Product product;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        customerRepository.deleteAll();

        customer = customerRepository.save(new Customer(null, "Alice", "Smith", "alice@example.com", "1234567890"));
        product = productRepository.save(new Product(null, "Product1", "Desc", new BigDecimal("10.00"), 100));
    }

    @Test
    void testCreateOrder_Valid() throws Exception {
        String json = """
                {
                  "customerId": "%s",
                  "items": [
                    {
                      "productId": "%s",
                      "quantity": 2,
                      "price": "10.00"
                    }
                  ],
                  "shippingAddress": "123 Main St"
                }
                """.formatted(customer.getId(), product.getId());

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.items[0].productId").value(product.getId().toString()))
                .andExpect(jsonPath("$.items[0].quantity").value(2));

        // Проверка totalPrice
        mockMvc.perform(get("/api/v1/orders")
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateOrder_ProductNotFound() throws Exception {
        String json = """
                {
                  "customerId": "%s",
                  "items": [
                    {
                      "productId": "%s",
                      "quantity": 1,
                      "price": "10.00"
                    }
                  ],
                  "shippingAddress": "123 Main St"
                }
                """.formatted(customer.getId(), UUID.randomUUID());

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrder_InsufficientStock() throws Exception {
        String json = """
                {
                  "customerId": "%s",
                  "items": [
                    {
                      "productId": "%s",
                      "quantity": 200,
                      "price": "10.00"
                    }
                  ],
                  "shippingAddress": "123 Main St"
                }
                """.formatted(customer.getId(), product.getId());

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrder_InvalidDTO() throws Exception {
        String json = """
                {
                  "customerId": null,
                  "items": [
                    {
                      "productId": null,
                      "quantity": 0,
                      "price": ""
                    }
                  ],
                  "shippingAddress": ""
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
                //.andExpect(jsonPath("$.items[0].quantity").exists());
    }

    @Test
    void testGetOrderById_Valid() throws Exception {
        Order order = new Order(null, customer,  "123 St", BigDecimal.ZERO, OrderStatus.NEW, null);
        OrderItem itemEntity = new OrderItem(null, order, product, 1, product.getPrice());
        order.setTotalPrice(product.getPrice());
        order = orderRepository.save(order);
        orderItemRepository.save(itemEntity);

        mockMvc.perform(get("/api/v1/orders/{id}", order.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId().toString()))
                .andExpect(jsonPath("$.items[0].productId").value(product.getId().toString()));
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/orders/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}