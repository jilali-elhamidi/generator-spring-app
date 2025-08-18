package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.InvoiceSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;

    private String companyName;

    private String contactPerson;

    private String email;

    private String phoneNumber;

    private List<ProjectSimpleDto> projects;

    private List<InvoiceSimpleDto> invoices;

}