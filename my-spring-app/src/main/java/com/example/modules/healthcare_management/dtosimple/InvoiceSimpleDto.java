package com.example.modules.healthcare_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSimpleDto {

    private Long id;

    private Date invoiceDate;

    private Double totalAmount;

    private String status;

}