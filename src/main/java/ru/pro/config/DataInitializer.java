package ru.pro.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.pro.model.entity.OrderEntity;
import ru.pro.model.entity.UserEntity;
import ru.pro.repository.OrderRepository;
import ru.pro.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private static final List<String> PRODUCT_NAMES = List.of(
            "Laptop", "Phone", "Tablet", "Headphones", "Monitor",
            "Keyboard", "Mouse", "Smartwatch", "Printer", "Camera"
    );

    private final Random random = new Random();

    @Override
    public void run(String... args) {
        orderRepository.deleteAll();
        userRepository.deleteAll();

        // Создаем 10 пользователей
        List<UserEntity> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            users.add(new UserEntity(null, "User" + i, "user" + i + "@example.com"));
        }
        userRepository.saveAll(users);

        System.out.println("Users:");
        userRepository.findAll().forEach(System.out::println);

        // Для каждого пользователя создаем по 10 заказов
        List<OrderEntity> orders = new ArrayList<>();
        for (UserEntity user : users) {
            for (int j = 0; j < 10; j++) {
                OrderEntity order = new OrderEntity();
                order.setUser(user);

                String productName = PRODUCT_NAMES.get(random.nextInt(PRODUCT_NAMES.size()));
                order.setName(productName);

                order.setItems(random.nextInt(5) + 1); // от 1 до 5 штук
                order.setAmount(BigDecimal.valueOf(10 + (500 - 10) * random.nextDouble()) // от 10 до 500
                        .setScale(2, RoundingMode.HALF_UP));

                orders.add(order);
            }
        }
        orderRepository.saveAll(orders);

        System.out.println("Orders:");
        orderRepository.findAll().forEach(System.out::println);
    }
}
