package ru.pro.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.pro.model.dto.AuthorDto;
import ru.pro.model.entity.AuthorEntity;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(AuthorEntity entity);

    AuthorEntity toEntity(AuthorDto dto);

    List<AuthorDto> toDtoList(List<AuthorEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(AuthorDto source, @MappingTarget AuthorEntity target);
}
