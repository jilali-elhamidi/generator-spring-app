package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;


import com.example.modules.healthcare_management.dtosimple.PatientSimpleDto;

import java.util.List;
import com.example.modules.healthcare_management.dtosimple.ReimbursementSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private Long id;

    private Date invoiceDate;

    private Double totalAmount;

    private String status;


    private PatientSimpleDto patient;

    private List<ReimbursementSimpleDto> Reimbursements;

}