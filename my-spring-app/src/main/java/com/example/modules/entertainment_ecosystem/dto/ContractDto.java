package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.EventSponsorshipSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {

    private Long id;

    private String contractNumber;

    private String terms;

    private EventSponsorshipSimpleDto sponsorship;

}