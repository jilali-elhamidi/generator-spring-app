package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.dto.UserDto;
import com.example.modules.ecommerce.dto.UserSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


UserDto toDto(User user);

UserSimpleDto toSimpleDto(User user);

@InheritInverseConfiguration
User toEntity(UserDto userDto);

List<UserDto> toDtoList(List<User> userList);

    List<User> toEntityList(List<UserDto> userDtoList);
        }