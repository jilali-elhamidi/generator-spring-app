package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameAchievementSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameSessionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.DevelopmentStudioSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentTagSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameRatingSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewUpvoteSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewDownvoteSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingBoardSimpleDto;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.model.Publisher;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoGameDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private Artist developer;

    private Publisher publisher;

    private String platform;

    private List<GenreSimpleDto> genres;

    private List<ReviewSimpleDto> generalReviews;

    private List<GameReviewSimpleDto> gameReviews;

    private List<UserProfileSimpleDto> playedBy;

    private List<GameAchievementSimpleDto> achievements;

    private List<GameSessionSimpleDto> sessions;

    private DevelopmentStudioSimpleDto developerStudio;

    private List<DigitalPurchaseSimpleDto> purchases;

    private List<GamePlatformSimpleDto> platforms;

    private ContentRatingSimpleDto contentRating;

    private List<ContentTagSimpleDto> tags;

    private List<VideoGameRatingSimpleDto> ratings;

    private List<GamePlaySessionSimpleDto> gamePlaySessions;

    private List<GameReviewUpvoteSimpleDto> gameReviewUpvotes;

    private List<GameReviewDownvoteSimpleDto> gameReviewDownvotes;

    private ContentRatingBoardSimpleDto contentRatingBoard;

}