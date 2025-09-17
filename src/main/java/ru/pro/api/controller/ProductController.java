package ru.pro.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.ProductApi;
import ru.pro.model.dto.ProductDto;
import ru.pro.service.ProductService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Override
    public ResponseEntity<ProductDto> findById(@Valid UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Override
    public ResponseEntity<ProductDto> create(@Valid ProductDto dto) {
        ProductDto created = productService.create(dto);
        return ResponseEntity.status(CREATED).body(created);
    }

    @Override
    public ResponseEntity<ProductDto> update(@Valid UUID id, @Valid ProductDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> deleteById(@Valid UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
