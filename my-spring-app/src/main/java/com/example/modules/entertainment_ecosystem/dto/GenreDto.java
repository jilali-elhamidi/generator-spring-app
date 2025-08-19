package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AlbumSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

    private Long id;

    private String name;

    private List<MovieSimpleDto> movies;

    private List<TVShowSimpleDto> tvShows;

    private List<BookSimpleDto> bookGenres;

    private List<MusicTrackSimpleDto> musicTracks;

    private List<UserProfileSimpleDto> favoriteUsers;

    private List<VideoGameSimpleDto> videoGames;

    private List<PodcastSimpleDto> podcasts;

    private List<AlbumSimpleDto> albums;

}