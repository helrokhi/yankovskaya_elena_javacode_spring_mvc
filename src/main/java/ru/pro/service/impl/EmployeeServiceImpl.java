package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.mapper.EmployeeMapper;
import ru.pro.model.dto.EmployeeDto;
import ru.pro.model.entity.DepartmentEntity;
import ru.pro.model.entity.EmployeeEntity;
import ru.pro.projections.EmployeeProjection;
import ru.pro.repository.DepartmentRepository;
import ru.pro.repository.EmployeeRepository;
import ru.pro.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDto create(EmployeeDto dto) {
        if (!departmentRepository.existsById(UUID.fromString(dto.departmentId()))) {
            throw new EntityNotFoundException("Department not found with id: " + dto.departmentId());
        }
        EmployeeEntity entity = employeeMapper.toEntity(dto);

        EmployeeEntity saved = employeeRepository.save(entity);
        return employeeMapper.toDto(saved);
    }

    @Override
    public List<EmployeeProjection> findAll() {
        return employeeRepository.findAllProjectedBy();
    }

    @Override
    public EmployeeProjection findById(UUID id) {
        return employeeRepository.findProjectedById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public EmployeeDto update(UUID id, EmployeeDto dto) {
        EmployeeEntity entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        DepartmentEntity department = departmentRepository.findById(UUID.fromString(dto.departmentId()))
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.departmentId()));

        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setPosition(dto.position());
        entity.setDepartment(department);

        EmployeeEntity updated = employeeRepository.save(entity);
        return employeeMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
