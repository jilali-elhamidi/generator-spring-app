package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.healthcare_management.dtosimple.PrescriptionSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto {

    private Long id;

    private String name;

    private String type;

    private String manufacturer;

    private List<PrescriptionSimpleDto> prescriptions;

}