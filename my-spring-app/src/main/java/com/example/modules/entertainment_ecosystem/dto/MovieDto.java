package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ProductionCompanySimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieFormatSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentProviderSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieStudioSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentTagSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentLanguageSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private Integer durationMinutes;

    private String synopsis;

    private Double boxOfficeRevenue;

    private List<ArtistSimpleDto> cast;

    private ArtistSimpleDto director;

    private List<ReviewSimpleDto> reviews;

    private List<GenreSimpleDto> genres;

    private List<UserProfileSimpleDto> watchlistUsers;

    private List<MerchandiseSimpleDto> relatedMerchandise;

    private ProductionCompanySimpleDto productionCompany;

    private List<DigitalPurchaseSimpleDto> purchases;

    private List<MovieFormatSimpleDto> formats;

    private List<StreamingContentLicenseSimpleDto> streamingLicenses;

    private ContentProviderSimpleDto provider;

    private MovieStudioSimpleDto movieStudio;

    private ContentRatingSimpleDto contentRating;

    private List<ContentTagSimpleDto> tags;

    private List<ContentLanguageSimpleDto> languages;

}