package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseTypeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseDto {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private ArtistSimpleDto artist;

    private List<MovieSimpleDto> relatedMovies;

    private List<TVShowSimpleDto> relatedShows;

    private List<UserProfileSimpleDto> ownedByUsers;

    private MerchandiseTypeSimpleDto productType;

}