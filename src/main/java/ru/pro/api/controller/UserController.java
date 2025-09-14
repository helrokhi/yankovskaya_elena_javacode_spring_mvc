package ru.pro.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.UserApi;
import ru.pro.model.dto.UserDto;
import ru.pro.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Override
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Override
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @Override
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
