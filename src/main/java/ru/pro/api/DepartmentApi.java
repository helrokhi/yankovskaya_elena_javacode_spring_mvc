package ru.pro.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.DepartmentDto;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/v1/departments")
public interface DepartmentApi {
    @GetMapping
    default ResponseEntity<List<DepartmentDto>> findAll() {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}")
    default ResponseEntity<DepartmentDto> findById(@Valid @PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PostMapping
    default ResponseEntity<DepartmentDto> create(@Valid @RequestBody DepartmentDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PutMapping("/{id}")
    default ResponseEntity<DepartmentDto> update(@Valid @PathVariable UUID id, @Valid @RequestBody DepartmentDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    default ResponseEntity<Void> deleteById(@Valid @PathVariable UUID id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
