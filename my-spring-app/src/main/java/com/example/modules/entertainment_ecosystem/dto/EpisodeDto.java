package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.SeasonSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;





@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto {

    private Long id;

    private Integer episodeNumber;

    private String title;

    private String description;

    private Integer durationMinutes;

    private SeasonSimpleDto season;

    private List<UserProfileSimpleDto> watchedByUsers;

    

}