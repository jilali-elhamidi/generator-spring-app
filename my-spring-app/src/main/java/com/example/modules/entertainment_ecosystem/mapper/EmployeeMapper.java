package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.dto.EmployeeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseMapper<Employee, EmployeeDto, EmployeeSimpleDto> {
}