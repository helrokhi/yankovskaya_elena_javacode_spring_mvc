package ru.pro.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.pro.mapper.OrderMapper;
import ru.pro.mapper.UserMapper;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.OrderEntity;
import ru.pro.model.entity.UserEntity;
import ru.pro.model.response.PagedResponse;
import ru.pro.repository.OrderRepository;
import ru.pro.repository.UserRepository;
import ru.pro.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.pro.model.enums.OrderStatus.NEW;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private UserEntity userEntity;
    private UserDto userDto;
    private OrderEntity orderEntity;
    private OrderDto orderDto;


    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        userEntity = new UserEntity(userId, "John", "john@example.com");
        orderEntity = new OrderEntity(orderId, userEntity, "laptop", 1, BigDecimal.valueOf(89.99), NEW);

        orderDto = new OrderDto(orderId, "laptop", 1, BigDecimal.valueOf(89.99), NEW);

        userDto = new UserDto(userId, "John", "john@example.com", List.of(orderDto));
    }

    @Test
    void findAll_shouldReturnListOfUsers() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));
        Page<UserEntity> entityPage = new PageImpl<>(List.of(userEntity), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(entityPage);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        PagedResponse<UserDto> result = userService.findAll(pageable);

        assertEquals(1, result.totalElements());
        assertEquals(1, result.content().size());
        assertEquals("John", result.content().getFirst().name());
        assertEquals(5, result.size());
        assertEquals(0, result.number());
        assertEquals(1, result.totalPages());

        Sort sort = pageable.getSort();
        assertEquals(Sort.by(Sort.Direction.ASC, "name"), sort);
    }

    @Test
    void findById_shouldReturnUserWithOrders() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(orderRepository.findByUserId(userId)).thenReturn(List.of(orderEntity));
        when(orderMapper.toDtoList(List.of(orderEntity))).thenReturn(List.of(orderDto));
        when(userMapper.userDtoToUserEntity(userEntity, List.of(orderDto))).thenReturn(userDto);

        UserDto result = userService.findById(userId);

        assertEquals(userId, result.id());
        verify(userRepository).findById(userId);
        verify(orderRepository).findByUserId(userId);
    }

    @Test
    void findById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    void create_shouldSaveUser() {
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto result = userService.create(userDto);

        assertEquals(userId, result.id());
        verify(userRepository).save(userEntity);
    }

    @Test
    void update_shouldModifyAndReturnUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        doAnswer(inv -> {
            UserDto src = inv.getArgument(0);
            UserEntity tgt = inv.getArgument(1);
            tgt.setName(src.name());
            return null;
        }).when(userMapper).updateEntity(eq(userDto), any(UserEntity.class));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto result = userService.update(userId, userDto);

        assertEquals("John", result.name());
        verify(userRepository).save(userEntity);
    }

    @Test
    void update_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(userId, userDto));
    }

    @Test
    void delete_shouldRemoveUser() {
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void delete_shouldThrowException_whenUserNotFound() {
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, never()).deleteById(any());
    }

}