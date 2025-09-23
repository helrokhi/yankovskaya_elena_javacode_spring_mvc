package ru.pro.service;

import ru.pro.model.dto.EmployeeDto;
import ru.pro.projections.EmployeeProjection;

public interface EmployeeService extends CrudService<EmployeeDto, EmployeeProjection> {
}
