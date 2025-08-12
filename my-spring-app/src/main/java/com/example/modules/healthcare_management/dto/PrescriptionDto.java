package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;


import com.example.modules.healthcare_management.dtosimple.PatientSimpleDto;

import com.example.modules.healthcare_management.dtosimple.DoctorSimpleDto;

import java.util.List;
import com.example.modules.healthcare_management.dtosimple.MedicationSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {

    private Long id;

    private Date prescriptionDate;

    private String dosageInstructions;


    private PatientSimpleDto patient;

    private DoctorSimpleDto doctor;

    private List<MedicationSimpleDto> medications;

}