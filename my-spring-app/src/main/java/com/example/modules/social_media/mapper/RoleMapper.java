package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface RoleMapper {

RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

RoleDto toDto(Role role);

Role toEntity(RoleDto roleDto);

List<RoleDto> toDtoList(List<Role> roleList);

List<Role> toEntityList(List<RoleDto> roleDtoList);
}