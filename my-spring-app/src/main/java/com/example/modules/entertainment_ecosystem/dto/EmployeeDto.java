package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ProductionCompanySimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ShiftSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String position;

    private ProductionCompanySimpleDto productionCompany;

    private List<ShiftSimpleDto> shifts;

}