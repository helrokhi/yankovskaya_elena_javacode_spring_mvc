package ru.pro.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.pro.model.dto.OrderDto;
import ru.pro.model.dto.UserDto;
import ru.pro.model.entity.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-14T20:26:03+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (BellSoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String email = null;

        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();

        List<OrderDto> orders = null;

        UserDto userDto = new UserDto( id, name, email, orders );

        return userDto;
    }

    @Override
    public UserDto userDtoToUserEntity(UserEntity entity, List<OrderDto> orders) {
        if ( entity == null && orders == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String email = null;
        if ( entity != null ) {
            id = entity.getId();
            name = entity.getName();
            email = entity.getEmail();
        }
        List<OrderDto> orders1 = null;
        List<OrderDto> list = orders;
        if ( list != null ) {
            orders1 = new ArrayList<OrderDto>( list );
        }

        UserDto userDto = new UserDto( id, name, email, orders1 );

        return userDto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( dto.id() );
        userEntity.setName( dto.name() );
        userEntity.setEmail( dto.email() );

        return userEntity;
    }

    @Override
    public List<UserDto> toDtoList(List<UserEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entities.size() );
        for ( UserEntity userEntity : entities ) {
            list.add( toDto( userEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntity(UserDto source, UserEntity target) {
        if ( source == null ) {
            return;
        }

        if ( source.name() != null ) {
            target.setName( source.name() );
        }
        if ( source.email() != null ) {
            target.setEmail( source.email() );
        }
    }
}
