package ru.pro.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pro.mapper.OrderMapper;
import ru.pro.mapper.UserMapper;
import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.OrderEntity;
import ru.pro.model.entity.UserEntity;
import ru.pro.repository.OrderRepository;
import ru.pro.repository.UserRepository;
import ru.pro.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserDto findById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        List<OrderEntity> entities = orderRepository.findByUserId(id);
        return userMapper.userDtoToUserEntity(user, orderMapper.toDtoList(entities));
    }

    @Override
    @Transactional
    public UserDto create(UserDto dto) {
        UserEntity entity = userMapper.toEntity(dto);
        UserEntity saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public UserDto update(UUID id, UserDto source) {
        UserEntity target = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        userMapper.updateEntity(source, target);
        UserEntity saved = userRepository.save(target);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
