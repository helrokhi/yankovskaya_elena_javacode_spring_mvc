package ru.pro.mapper;

import org.mapstruct.Mapper;
import ru.pro.model.dto.AuthorDto;
import ru.pro.model.entity.AuthorEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(AuthorEntity entity);

    AuthorEntity toEntity(AuthorDto dto);

    List<AuthorDto> toDtoList(List<AuthorEntity> entities);
}
