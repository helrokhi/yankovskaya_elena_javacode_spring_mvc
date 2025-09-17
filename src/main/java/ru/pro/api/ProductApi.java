package ru.pro.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.ProductDto;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/v1/products")
public interface ProductApi {

    @GetMapping
    default ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}")
    default ResponseEntity<ProductDto> findById(@Valid @PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PostMapping
    default ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PutMapping("/{id}")
    default ResponseEntity<ProductDto> update(@Valid @PathVariable UUID id, @Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    default ResponseEntity<Void> deleteById(@Valid @PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
