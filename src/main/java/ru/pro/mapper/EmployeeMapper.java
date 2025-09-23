package ru.pro.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.pro.model.dto.EmployeeDto;
import ru.pro.model.entity.EmployeeEntity;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "departmentId", source = "department.id")
    EmployeeDto toDto(EmployeeEntity entity);

    @Mapping(target = "department.id", source = "departmentId")
    EmployeeEntity toEntity(EmployeeDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(EmployeeDto source, @MappingTarget EmployeeEntity target);
}
