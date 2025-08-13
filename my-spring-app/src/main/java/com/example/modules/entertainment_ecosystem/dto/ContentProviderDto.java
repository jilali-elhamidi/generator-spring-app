package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentProviderDto {

    private Long id;

    private String name;

    private String contactEmail;

    private List<MovieSimpleDto> providedMovies;

    private List<TVShowSimpleDto> providedTvShows;

    private List<MusicTrackSimpleDto> providedMusicTracks;

    private List<PodcastSimpleDto> providedPodcasts;

}