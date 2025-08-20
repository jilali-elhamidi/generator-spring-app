package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EventSponsorshipSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AdCampaignSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorDto {

    private Long id;

    private String name;

    private String contactEmail;

    private String companyType;

    private List<LiveEventSimpleDto> sponsoredEvents;

    private List<EventSponsorshipSimpleDto> sponsorships;

    private List<AdCampaignSimpleDto> adCampaigns;

}