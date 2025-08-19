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

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentProviderSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowStudioSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentTagSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentLanguageSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;



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

    private List<ArtistSimpleDto> cast;

    private List<StreamingContentLicenseSimpleDto> streamingLicenses;

    private ContentProviderSimpleDto provider;

    private TVShowStudioSimpleDto tvShowStudio;

    private ContentRatingSimpleDto contentRating;

    private List<ContentTagSimpleDto> tags;

    private List<ContentLanguageSimpleDto> languages;

    private List<StreamingPlatformSimpleDto> platforms;

}