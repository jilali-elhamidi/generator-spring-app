package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Role;
import com.example.modules.social_media.dto.RoleDto;
import com.example.modules.social_media.dtosimple.RoleSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(Role role);

    RoleSimpleDto toSimpleDto(Role role);

    @InheritInverseConfiguration
    Role toEntity(RoleDto roleDto);

    List<RoleDto> toDtoList(List<Role> roleList);

    List<Role> toEntityList(List<RoleDto> roleDtoList);

    void updateEntityFromDto(RoleDto dto, @MappingTarget Role entity);

}