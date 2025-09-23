package ru.pro.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.DepartmentApi;
import ru.pro.model.dto.DepartmentDto;
import ru.pro.service.DepartmentService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentApi {
    private final DepartmentService departmentService;

    @Override
    public ResponseEntity<List<DepartmentDto>> findAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @Override
    public ResponseEntity<DepartmentDto> findById(String id) {
        return ResponseEntity.ok(departmentService.findById(UUID.fromString(id)));
    }

    @Override
    public ResponseEntity<DepartmentDto> create(DepartmentDto dto) {
        DepartmentDto created = departmentService.create(dto);
        return ResponseEntity.status(CREATED).body(created);
    }

    @Override
    public ResponseEntity<DepartmentDto> update(String id, DepartmentDto dto) {
        return ResponseEntity.ok(departmentService.update(UUID.fromString(id), dto));
    }

    @Override
    public ResponseEntity<Void> deleteById(String id) {
        departmentService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
