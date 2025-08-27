package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.dto.RoleDto;
import com.example.modules.social_media.dtosimple.RoleSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDto, RoleSimpleDto> {
}