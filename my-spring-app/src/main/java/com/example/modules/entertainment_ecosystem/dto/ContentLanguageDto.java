package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentLanguageDto {

    private Long id;

    private String name;

    private String code;

    private List<MovieSimpleDto> movies;

    private List<TVShowSimpleDto> tvShows;

    private List<PodcastSimpleDto> podcasts;

}