package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EmployeeRole;
import com.example.modules.entertainment_ecosystem.dto.EmployeeRoleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeRoleSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EmployeeRoleMapper {

    EmployeeRoleMapper INSTANCE = Mappers.getMapper(EmployeeRoleMapper.class);

    EmployeeRoleDto toDto(EmployeeRole employeerole);

    EmployeeRoleSimpleDto toSimpleDto(EmployeeRole employeerole);

    @InheritInverseConfiguration
    EmployeeRole toEntity(EmployeeRoleDto employeeroleDto);

    List<EmployeeRoleDto> toDtoList(List<EmployeeRole> employeeroleList);

    List<EmployeeRole> toEntityList(List<EmployeeRoleDto> employeeroleDtoList);

    void updateEntityFromDto(EmployeeRoleDto dto, @MappingTarget EmployeeRole entity);

}