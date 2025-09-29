package ru.pro.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.OrderDto;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/v1/orders")
public interface OrderApi {

    @GetMapping("/{id}")
    default ResponseEntity<OrderDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PostMapping
    default ResponseEntity<OrderDto> create(@RequestBody OrderDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
