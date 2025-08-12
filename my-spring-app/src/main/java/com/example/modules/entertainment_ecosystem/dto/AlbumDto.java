package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private ArtistSimpleDto artist;

    private List<MusicTrackSimpleDto> tracks;

    private List<GenreSimpleDto> genres;

}