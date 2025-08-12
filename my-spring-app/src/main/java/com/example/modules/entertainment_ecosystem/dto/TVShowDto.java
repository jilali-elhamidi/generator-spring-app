package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.SeasonSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ProductionCompanySimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVShowDto {

    private Long id;

    private String title;

    private Integer releaseYear;

    private Integer totalSeasons;

    private String synopsis;

    private List<SeasonSimpleDto> seasons;

    private ArtistSimpleDto director;

    private List<GenreSimpleDto> genres;

    private List<MerchandiseSimpleDto> relatedMerchandise;

    private ProductionCompanySimpleDto productionCompany;

}