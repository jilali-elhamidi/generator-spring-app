package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.dto.EmployeeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toDto(Employee employee);

    EmployeeSimpleDto toSimpleDto(Employee employee);

    @InheritInverseConfiguration
    Employee toEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toDtoList(List<Employee> employeeList);

    List<Employee> toEntityList(List<EmployeeDto> employeeDtoList);

}