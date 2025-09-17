package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.pro.mapper.ProductMapper;
import ru.pro.model.dto.ProductDto;
import ru.pro.model.entity.Product;
import ru.pro.repository.ProductRepository;
import ru.pro.service.ProductService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto findById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found" + id));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ProductDto update(UUID id, ProductDto dto) {
        Product target = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found" + id));
        productMapper.updateEntity(dto, target);
        Product updated = productRepository.save(target);
        return productMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Product not found" + id);
        }
    }
}
