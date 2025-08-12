package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSimpleDto {
private Long id;

    private String method;

    private Date paymentDate;

    private Double amount;

}