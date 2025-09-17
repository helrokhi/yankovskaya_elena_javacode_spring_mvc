package ru.pro.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pro.mapper.AuthLogMapper;
import ru.pro.model.dto.AuthLogDto;
import ru.pro.repository.AuthLogRepository;
import ru.pro.service.AuthLogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthLogServiceImpl implements AuthLogService {
    private final AuthLogRepository repository;
    private final AuthLogMapper mapper;

    @Override
    public void log(AuthLogDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public List<AuthLogDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
