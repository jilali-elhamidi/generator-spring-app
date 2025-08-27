package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.dto.DepartmentDto;
import com.example.modules.healthcare_management.dtosimple.DepartmentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends BaseMapper<Department, DepartmentDto, DepartmentSimpleDto> {
}