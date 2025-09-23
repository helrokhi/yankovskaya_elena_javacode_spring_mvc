package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.mapper.DepartmentMapper;
import ru.pro.model.dto.DepartmentDto;
import ru.pro.model.entity.DepartmentEntity;
import ru.pro.repository.DepartmentRepository;
import ru.pro.service.DepartmentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional
    public DepartmentDto create(DepartmentDto dto) {
        DepartmentEntity entity = departmentMapper.toEntity(dto);
        DepartmentEntity saved = departmentRepository.save(entity);
        return departmentMapper.toDto(saved);
    }

    @Override
    public List<DepartmentDto> findAll() {
        List<DepartmentEntity> entities = departmentRepository.findAll();
        return departmentMapper.toDtoList(entities);
    }


    @Override
    public DepartmentDto findById(UUID id) {
        DepartmentEntity entity = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        return departmentMapper.toDto(entity);
    }

    @Override
    @Transactional
    public DepartmentDto update(UUID id, DepartmentDto dto) {
        DepartmentEntity target = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));

        departmentMapper.updateEntity(dto, target);

        DepartmentEntity updated = departmentRepository.save(target);
        return departmentMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}