package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.TransactionSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalPurchaseDto {

    private Long id;

    private Date purchaseDate;

    private Double price;

    private UserProfileSimpleDto user;

    private MovieSimpleDto movie;

    private MusicTrackSimpleDto musicTrack;

    private VideoGameSimpleDto videoGame;

    private TransactionSimpleDto transaction;

}