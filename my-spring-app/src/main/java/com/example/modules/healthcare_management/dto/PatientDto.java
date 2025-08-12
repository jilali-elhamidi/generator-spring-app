package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;


import java.util.List;
import com.example.modules.healthcare_management.dtosimple.MedicalRecordSimpleDto;

import java.util.List;
import com.example.modules.healthcare_management.dtosimple.AppointmentSimpleDto;

import java.util.List;
import com.example.modules.healthcare_management.dtosimple.PrescriptionSimpleDto;

import java.util.List;
import com.example.modules.healthcare_management.dtosimple.InvoiceSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String gender;

    private String email;

    private String phoneNumber;


    private List<MedicalRecordSimpleDto> medicalRecords;

    private List<AppointmentSimpleDto> appointments;

    private List<PrescriptionSimpleDto> prescriptions;

    private List<InvoiceSimpleDto> invoices;

}