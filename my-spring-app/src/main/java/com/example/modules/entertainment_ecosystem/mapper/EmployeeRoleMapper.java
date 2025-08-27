package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EmployeeRole;
import com.example.modules.entertainment_ecosystem.dto.EmployeeRoleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeRoleSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EmployeeRoleMapper extends BaseMapper<EmployeeRole, EmployeeRoleDto, EmployeeRoleSimpleDto> {
}