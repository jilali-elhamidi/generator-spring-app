package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentTagDto {

    private Long id;

    private String name;

    private List<MovieSimpleDto> movies;

    private List<TVShowSimpleDto> tvShows;

    private List<VideoGameSimpleDto> videoGames;

    private List<LiveEventSimpleDto> liveEvents;

}