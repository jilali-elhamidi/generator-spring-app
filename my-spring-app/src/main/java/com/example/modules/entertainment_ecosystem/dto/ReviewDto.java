package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.BookSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    private Integer rating;

    private String comment;

    private Date reviewDate;

    private UserProfileSimpleDto user;

    private MovieSimpleDto movie;

    private BookSimpleDto book;

    private VideoGameSimpleDto videoGame;

}