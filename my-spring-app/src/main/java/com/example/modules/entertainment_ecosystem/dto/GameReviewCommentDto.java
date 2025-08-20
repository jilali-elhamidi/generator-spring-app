package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameReviewCommentDto {

    private Long id;

    private String commentText;

    private Date commentDate;

    private UserProfileSimpleDto user;

    private GameReviewSimpleDto review;

    private List<GameReviewCommentSimpleDto> replies;

    private GameReviewCommentSimpleDto parentComment;

}