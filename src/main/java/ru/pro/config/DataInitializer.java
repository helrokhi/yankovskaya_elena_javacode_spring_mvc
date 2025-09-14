package ru.pro.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.pro.model.entity.OrderEntity;
import ru.pro.model.entity.UserEntity;
import ru.pro.repository.OrderRepository;
import ru.pro.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        orderRepository.deleteAll();
        userRepository.deleteAll();

        // Создаём пользователей
        UserEntity alice = new UserEntity(null, "Alice", "alice@example.com");
        UserEntity bob = new UserEntity(null, "Bob", "bob@example.com");
        userRepository.saveAll(List.of(alice, bob));

        System.out.println("Users:");
        userRepository.findAll().forEach(System.out::println);

        OrderEntity order1 = new OrderEntity();
        order1.setUser(alice);
        order1.setName("laptop");
        order1.setItems(2);
        order1.setAmount(BigDecimal.valueOf(89.99));

        OrderEntity order2 = new OrderEntity();
        order2.setUser(bob);
        order2.setName("phone");
        order2.setItems(1);
        order2.setAmount(BigDecimal.valueOf(33.15));

        orderRepository.saveAll(List.of(order1, order2));

        System.out.println("Orders:");
        orderRepository.findAll().forEach(System.out::println);
    }
}
