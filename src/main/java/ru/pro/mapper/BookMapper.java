package ru.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pro.model.dto.BookDto;
import ru.pro.model.entity.BookEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(BookEntity order);

    @Mapping(target = "author", ignore = true)
    BookEntity toEntity(BookDto dto);

    List<BookDto> toDtoList(List<BookEntity> entities);
}
