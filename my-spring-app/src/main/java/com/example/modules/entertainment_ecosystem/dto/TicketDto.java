package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TicketStatusSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.BookingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EventTicketTypeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private Long id;

    private String ticketNumber;

    private Double price;

    private String seatInfo;

    private UserProfileSimpleDto user;

    private LiveEventSimpleDto event;

    private TicketStatusSimpleDto status;

    private BookingSimpleDto booking;

    private EventTicketTypeSimpleDto type;

}