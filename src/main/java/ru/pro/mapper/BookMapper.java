package ru.pro.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.pro.model.dto.BookDto;
import ru.pro.model.entity.BookEntity;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(BookEntity entity);

    @Mapping(target = "author", ignore = true)
    BookEntity toEntity(BookDto dto);

    List<BookDto> toDtoList(List<BookEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(BookDto source, @MappingTarget BookEntity target);
}
