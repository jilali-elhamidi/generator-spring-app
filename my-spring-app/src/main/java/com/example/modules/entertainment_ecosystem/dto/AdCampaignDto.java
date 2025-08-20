package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.SponsorSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AdPlacementSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCampaignDto {

    private Long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private Double budget;

    private SponsorSimpleDto advertiser;

    private List<StreamingPlatformSimpleDto> displayedOnPlatforms;

    private List<AdPlacementSimpleDto> adPlacements;

}