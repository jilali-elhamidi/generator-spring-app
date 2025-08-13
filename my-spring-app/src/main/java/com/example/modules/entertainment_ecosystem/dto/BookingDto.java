package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PaymentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;

    private Date bookingDate;

    private Double totalAmount;

    private List<TicketSimpleDto> tickets;

    private PaymentSimpleDto payment;

}