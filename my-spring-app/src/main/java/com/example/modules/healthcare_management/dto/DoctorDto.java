package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


import java.util.List;
import com.example.modules.healthcare_management.dtosimple.AppointmentSimpleDto;

import java.util.List;
import com.example.modules.healthcare_management.dtosimple.PrescriptionSimpleDto;

import com.example.modules.healthcare_management.dtosimple.DepartmentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String specialty;

    private String email;

    private String phoneNumber;


    private List<AppointmentSimpleDto> appointments;

    private List<PrescriptionSimpleDto> prescriptions;

    private DepartmentSimpleDto department;

}