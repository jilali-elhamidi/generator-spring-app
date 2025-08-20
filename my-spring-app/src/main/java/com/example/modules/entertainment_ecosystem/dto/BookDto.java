package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PublisherSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.BookSeriesSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.BookCharacterSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private Date publishedDate;

    private String isbn;

    private String synopsis;

    private ArtistSimpleDto author;

    private PublisherSimpleDto publisher;

    private List<ReviewSimpleDto> reviews;

    private List<GenreSimpleDto> genres;

    private BookSeriesSimpleDto series;

    private List<BookCharacterSimpleDto> characters;

}