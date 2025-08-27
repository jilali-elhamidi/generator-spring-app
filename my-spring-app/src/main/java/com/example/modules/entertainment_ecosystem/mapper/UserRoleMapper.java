package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.dto.UserRoleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserRoleSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserRoleMapper extends BaseMapper<UserRole, UserRoleDto, UserRoleSimpleDto> {
}