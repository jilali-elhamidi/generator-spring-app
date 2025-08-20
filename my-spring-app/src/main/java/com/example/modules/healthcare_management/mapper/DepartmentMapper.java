package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Department;
import com.example.modules.healthcare_management.dto.DepartmentDto;
import com.example.modules.healthcare_management.dtosimple.DepartmentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDto toDto(Department department);

    DepartmentSimpleDto toSimpleDto(Department department);

    @InheritInverseConfiguration
    Department toEntity(DepartmentDto departmentDto);

    List<DepartmentDto> toDtoList(List<Department> departmentList);

    List<Department> toEntityList(List<DepartmentDto> departmentDtoList);

    void updateEntityFromDto(DepartmentDto dto, @MappingTarget Department entity);

}