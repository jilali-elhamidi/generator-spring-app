package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SponsorSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContractSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventSponsorshipDto {

    private Long id;

    private Double amount;

    private Date startDate;

    private Date endDate;

    private LiveEventSimpleDto event;

    private SponsorSimpleDto sponsor;

    private ContractSimpleDto contract;

}