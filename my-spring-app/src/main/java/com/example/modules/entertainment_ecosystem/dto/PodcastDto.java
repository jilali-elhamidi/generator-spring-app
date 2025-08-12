package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastEpisodeSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PublisherSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PodcastDto {

    private Long id;

    private String title;

    private String description;

    private Integer totalEpisodes;

    private ArtistSimpleDto host;

    private List<PodcastEpisodeSimpleDto> episodes;

    private List<GenreSimpleDto> genres;

    private List<UserProfileSimpleDto> listeners;

    private PublisherSimpleDto publisher;

}