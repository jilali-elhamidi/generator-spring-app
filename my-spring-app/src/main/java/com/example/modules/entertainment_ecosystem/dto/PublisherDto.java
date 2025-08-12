package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {

    private Long id;

    private String name;

    private String website;

    private List<BookSimpleDto> books;

    private List<PodcastSimpleDto> podcasts;

    private List<VideoGameSimpleDto> videoGames;

}