package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TicketSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EventTypeSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EventLocationSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SponsorSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveEventDto {

    private Long id;

    private String name;

    private Date eventDate;

    private String description;

    private List<ArtistSimpleDto> performers;

    private List<TicketSimpleDto> tickets;

    private EventTypeSimpleDto eventType;

    private EventLocationSimpleDto location;

    private SponsorSimpleDto sponsor;

}