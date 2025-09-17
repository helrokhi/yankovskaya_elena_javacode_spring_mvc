package ru.pro.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.pro.model.dto.ProductDto;
import ru.pro.model.entity.Product;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product entity);

    Product toEntity(ProductDto dto);

    List<ProductDto> toDtoList(List<Product> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(ProductDto source, @MappingTarget Product target);
}
