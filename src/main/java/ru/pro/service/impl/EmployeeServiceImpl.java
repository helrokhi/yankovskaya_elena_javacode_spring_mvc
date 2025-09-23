package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeDto create(EmployeeDto dto) {
        log.info("{} ", dto.departmentId());

        DepartmentEntity department = departmentRepository.findById(UUID.fromString(dto.departmentId()))
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.departmentId()));

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
    @Transactional
    public EmployeeDto update(UUID id, EmployeeDto dto) {
        EmployeeEntity target = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        log.info("{} ", target.getId());

        employeeMapper.updateEntity(dto, target);
        if (dto.departmentId() != null) {
            DepartmentEntity department = departmentRepository.findById(UUID.fromString(dto.departmentId()))
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.departmentId()));
            target.setDepartment(department);
            log.info("{} ", department.getId());
        }

        EmployeeEntity updated = employeeRepository.save(target);
        return employeeMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
