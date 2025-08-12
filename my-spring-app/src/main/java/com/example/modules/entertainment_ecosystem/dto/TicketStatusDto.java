package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.TicketSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusDto {

    private Long id;

    private String name;

    private List<TicketSimpleDto> tickets;

}