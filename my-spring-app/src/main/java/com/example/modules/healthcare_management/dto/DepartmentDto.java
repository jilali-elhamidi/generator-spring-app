package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.healthcare_management.dtosimple.DoctorSimpleDto;





@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private Long id;

    private String name;

    private String description;

    private List<DoctorSimpleDto> doctors;

    

}