package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AlbumSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ManagerSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    private Long id;

    private String name;

    private String bio;

    private Date birthDate;

    private String nationality;

    private List<MovieSimpleDto> actedInMovies;

    private List<MovieSimpleDto> directedMovies;

    private List<TVShowSimpleDto> directedShows;

    private List<UserProfileSimpleDto> favoriteArtists;

    private List<MusicTrackSimpleDto> composedMusic;

    private List<AlbumSimpleDto> albums;

    private List<BookSimpleDto> booksAuthored;

    private List<LiveEventSimpleDto> participatedInEvents;

    private List<PodcastSimpleDto> hostedPodcasts;

    private List<MerchandiseSimpleDto> managedMerchandise;

    private List<VideoGameSimpleDto> managedGames;

    private ManagerSimpleDto manager;

}