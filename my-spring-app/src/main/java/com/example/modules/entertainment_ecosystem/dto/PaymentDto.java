package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.BookingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PaymentMethodSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    private Double amount;

    private Date paymentDate;

    private BookingSimpleDto booking;

    private PaymentMethodSimpleDto method;

}