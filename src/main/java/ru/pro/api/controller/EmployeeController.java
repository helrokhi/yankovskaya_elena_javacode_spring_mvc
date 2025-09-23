package ru.pro.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.EmployeeApi;
import ru.pro.model.dto.EmployeeDto;
import ru.pro.projections.EmployeeProjection;
import ru.pro.service.EmployeeService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<EmployeeProjection>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Override
    public ResponseEntity<EmployeeProjection> findById(UUID id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @Override
    public ResponseEntity<EmployeeDto> create(EmployeeDto dto) {
        EmployeeDto created = employeeService.create(dto);
        return ResponseEntity.status(CREATED).body(created);
    }

    @Override
    public ResponseEntity<EmployeeDto> update(UUID id, EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
