package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlinePlatformSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamingPlatformDto {

    private Long id;

    private String name;

    private String website;

    private List<MovieSimpleDto> movies;

    private List<TVShowSimpleDto> tvShows;

    private List<SubscriptionSimpleDto> subscriptions;

    private OnlinePlatformSimpleDto onlinePlatform;

}