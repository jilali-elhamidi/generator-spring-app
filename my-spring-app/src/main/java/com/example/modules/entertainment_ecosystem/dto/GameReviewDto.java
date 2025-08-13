package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewUpvoteSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewDownvoteSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameReviewDto {

    private Long id;

    private Integer rating;

    private String comment;

    private UserProfileSimpleDto user;

    private VideoGameSimpleDto game;

    private List<GameReviewCommentSimpleDto> comments;

    private List<GameReviewUpvoteSimpleDto> upvotes;

    private List<GameReviewDownvoteSimpleDto> downvotes;

}