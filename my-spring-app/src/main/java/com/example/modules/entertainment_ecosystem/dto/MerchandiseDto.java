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

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseInventorySimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSaleSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSupplierSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderItemSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCollectionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCategorySimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseStockHistorySimpleDto;



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

    private MerchandiseInventorySimpleDto inventory;

    private List<MerchandiseReviewSimpleDto> reviews;

    private List<MerchandiseSaleSimpleDto> sales;

    private MerchandiseSupplierSimpleDto supplier;

    private List<MerchandiseShippingSimpleDto> shipments;

    private List<MerchandiseOrderItemSimpleDto> orderItems;

    private MerchandiseCollectionSimpleDto collection;

    private MerchandiseCategorySimpleDto category;

    private List<MerchandiseStockHistorySimpleDto> stockHistory;

}