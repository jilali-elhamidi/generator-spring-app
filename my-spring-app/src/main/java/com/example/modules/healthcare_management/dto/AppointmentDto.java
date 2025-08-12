package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


import com.example.modules.healthcare_management.dtosimple.PatientSimpleDto;

import com.example.modules.healthcare_management.dtosimple.DoctorSimpleDto;

import com.example.modules.healthcare_management.dtosimple.RoomSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    private Long id;

    private LocalDateTime appointmentDate;

    private String status;


    private PatientSimpleDto patient;

    private DoctorSimpleDto doctor;

    private RoomSimpleDto room;

}