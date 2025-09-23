package ru.pro.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.pro.model.dto.DepartmentDto;
import ru.pro.model.entity.DepartmentEntity;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDto toDto(DepartmentEntity entity);

    DepartmentEntity toEntity(DepartmentDto dto);

    List<DepartmentDto> toDtoList(List<DepartmentEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(DepartmentDto source, @MappingTarget DepartmentEntity target);
}
