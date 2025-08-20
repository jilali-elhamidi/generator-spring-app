package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameReviewUpvoteDto {

    private Long id;

    private Date upvoteDate;

    private UserProfileSimpleDto user;

    private GameReviewSimpleDto review;

    private VideoGameSimpleDto game;

}