package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.User;
import com.example.modules.ecommerce.dto.UserDto;
import com.example.modules.ecommerce.dtosimple.UserSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto, UserSimpleDto> {
}