package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSoundtrackDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private MovieSimpleDto movie;

    private List<MusicTrackSimpleDto> musicTracks;

}