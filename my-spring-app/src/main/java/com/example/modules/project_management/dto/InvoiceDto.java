package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.ClientSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private Long id;

    private Date invoiceDate;

    private Double amount;

    private String status;

    private ProjectSimpleDto project;

    private ClientSimpleDto client;

}