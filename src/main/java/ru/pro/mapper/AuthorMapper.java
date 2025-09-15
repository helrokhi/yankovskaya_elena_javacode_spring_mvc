package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.AuthorDto;
import ru.pro.model.entity.AuthorEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(AuthorEntity entity);

    @Mapping(target = "books", ignore = true)
    AuthorEntity toEntity(AuthorDto dto);

    List<AuthorDto> toDtoList(List<AuthorEntity> entities);
}
