package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorDto {

    private Long id;

    private String name;

    private String contactEmail;

    private List<LiveEventSimpleDto> sponsoredEvents;

    private List<MovieSimpleDto> sponsoredMovies;

    private List<TVShowSimpleDto> sponsoredShows;

}