package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingBoardSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentRatingDto {

    private Long id;

    private String name;

    private String description;

    private List<MovieSimpleDto> ratedMovies;

    private List<TVShowSimpleDto> ratedTvShows;

    private List<VideoGameSimpleDto> ratedVideoGames;

    private ContentRatingBoardSimpleDto board;

}