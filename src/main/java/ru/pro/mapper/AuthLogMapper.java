package ru.pro.mapper;

import org.mapstruct.Mapper;
import ru.pro.model.dto.AuthLogDto;
import ru.pro.model.entity.AuthLogEntity;

@Mapper(componentModel = "spring")
public interface AuthLogMapper {
    AuthLogEntity toEntity(AuthLogDto dto);

    AuthLogDto toDto(AuthLogEntity entity);
}
