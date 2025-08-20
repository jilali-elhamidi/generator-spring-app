package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicVideoDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private Integer durationSeconds;

    private MusicTrackSimpleDto musicTrack;

    private ArtistSimpleDto director;

}