package ru.pro.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pro.model.dto.EmployeeDto;
import ru.pro.projections.EmployeeProjection;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@RequestMapping("/api/v1/employees")
@Validated
public interface EmployeeApi {
    @GetMapping
    default ResponseEntity<List<EmployeeProjection>> findAll() {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}")
    default ResponseEntity<EmployeeProjection> findById(
            @PathVariable
            @NotBlank
            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "Invalid UUID format")
            String id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PostMapping
    default ResponseEntity<EmployeeDto> create(
            @RequestBody
            @Validated(EmployeeDto.OnCreate.class) EmployeeDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @PutMapping("/{id}")
    default ResponseEntity<EmployeeDto> update(
            @PathVariable
            @NotBlank
            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "Invalid UUID format")
            String id,
            @RequestBody
            @Validated(EmployeeDto.OnUpdate.class)
            EmployeeDto dto) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    default ResponseEntity<Void> deleteById(
            @PathVariable
            @NotBlank
            @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "Invalid UUID format")
            String id) {
        return new ResponseEntity<>(NOT_IMPLEMENTED);
    }
}
