package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PublisherSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookChapterSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudiobookDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private Double durationHours;

    private String narrator;

    private ArtistSimpleDto author;

    private PublisherSimpleDto publisher;

    private List<AudiobookChapterSimpleDto> chapters;

}