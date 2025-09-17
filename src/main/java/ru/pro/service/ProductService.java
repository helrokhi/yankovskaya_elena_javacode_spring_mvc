package ru.pro.service;

import ru.pro.model.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(UUID id);

    ProductDto create(ProductDto dto);

    ProductDto update(UUID id, ProductDto dto);

    void deleteById(UUID id);
}
