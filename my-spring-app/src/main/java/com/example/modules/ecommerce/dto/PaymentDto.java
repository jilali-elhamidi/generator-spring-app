package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    private String method;

    private Date paymentDate;

    private Double amount;

    private OrderSimpleDto order;

}