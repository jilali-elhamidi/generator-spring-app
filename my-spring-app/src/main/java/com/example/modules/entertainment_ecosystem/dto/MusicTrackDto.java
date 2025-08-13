package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.AlbumSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistItemSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicFormatSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentProviderSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicTrackDto {

    private Long id;

    private String title;

    private Integer durationSeconds;

    private Date releaseDate;

    private AlbumSimpleDto album;

    private ArtistSimpleDto artist;

    private GenreSimpleDto genre;

    private List<UserProfileSimpleDto> listenedByUsers;

    private List<PlaylistItemSimpleDto> playlistItems;

    private List<DigitalPurchaseSimpleDto> purchases;

    private List<MusicFormatSimpleDto> formats;

    private List<StreamingContentLicenseSimpleDto> streamingLicenses;

    private ContentProviderSimpleDto provider;

}