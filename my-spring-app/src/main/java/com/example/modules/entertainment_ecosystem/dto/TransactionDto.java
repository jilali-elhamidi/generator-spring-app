package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserWalletSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.InvoiceSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    private Double amount;

    private Date transactionDate;

    private String type;

    private UserWalletSimpleDto wallet;

    private InvoiceSimpleDto relatedInvoice;

}