package ru.pro.utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import ru.pro.model.entity.Customer;
import ru.pro.model.entity.Order;
import ru.pro.repository.CustomerRepository;
import ru.pro.repository.OrderRepository;

import java.util.UUID;

@UtilityClass
public class SecurityUtils {
    public UUID getCustomerId(Authentication authentication, CustomerRepository customerRepository) {
        String email = authentication.getName();
        return customerRepository.findByEmail(email)
                .map(Customer::getId)
                .orElse(null);
    }

    public Order getOrderByIdWithPermission(
            UUID id,
            Authentication authentication,
            CustomerRepository customerRepository,
            OrderRepository orderRepository) {
        if (hasAuthority(authentication, "order:read:all")) {
            return orderRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        }

        if (hasAuthority(authentication, "order:read:own")) {
            String email = authentication.getName();
            UUID customerId = customerRepository.findByEmail(email)
                    .map(Customer::getId)
                    .orElseThrow(() -> new AccessDeniedException("Customer not found"));

            return orderRepository.findByIdAndCustomerId(id, customerId)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        }

        throw new AccessDeniedException("Not enough permissions to read order");
    }

    public boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}
