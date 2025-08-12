package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.healthcare_management.dtosimple.InvoiceSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReimbursementDto {

    private Long id;

    private Date ReimbursementDate;

    private Double amount;

    private String method;

    private InvoiceSimpleDto invoice;

}