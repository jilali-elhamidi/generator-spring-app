package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamingContentLicenseDto {

    private Long id;

    private Date startDate;

    private Date endDate;

    private String region;

    private SubscriptionPlanSimpleDto subscriptionPlan;

    private MovieSimpleDto movie;

    private TVShowSimpleDto tvShow;

    private MusicTrackSimpleDto musicTrack;

}