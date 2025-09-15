package ru.pro.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pro.api.UserApi;
import ru.pro.model.dto.UserDto;
import ru.pro.model.response.PagedResponse;
import ru.pro.service.UserService;
import ru.pro.views.Views;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<PagedResponse<UserDto>> getAll(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC) //default page = 0
            Pageable pageable) {
        log.info("Page content: {}", userService.findAll(pageable));
        return ResponseEntity.ok(userService.findAll(pageable));
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
