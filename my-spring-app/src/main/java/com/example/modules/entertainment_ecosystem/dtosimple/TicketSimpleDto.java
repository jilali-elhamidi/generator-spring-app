package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSimpleDto {

    private Long id;

    private String ticketNumber;

    private Double price;

    private String seatInfo;

}