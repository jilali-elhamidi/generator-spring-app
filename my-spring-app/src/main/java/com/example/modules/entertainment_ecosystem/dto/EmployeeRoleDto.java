package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.EmployeeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRoleDto {

    private Long id;

    private String name;

    private String description;

    private List<EmployeeSimpleDto> employees;

}