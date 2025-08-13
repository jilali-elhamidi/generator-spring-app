package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserRole;
import com.example.modules.entertainment_ecosystem.dto.UserRoleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserRoleSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleDto toDto(UserRole userrole);

    UserRoleSimpleDto toSimpleDto(UserRole userrole);

    @InheritInverseConfiguration
    UserRole toEntity(UserRoleDto userroleDto);

    List<UserRoleDto> toDtoList(List<UserRole> userroleList);

    List<UserRole> toEntityList(List<UserRoleDto> userroleDtoList);

}