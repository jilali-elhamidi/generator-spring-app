package com.example.modules.entertainment_ecosystem.dto;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameAchievementSimpleDto;



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

    private List<ReviewSimpleDto> reviews;

    private List<UserProfileSimpleDto> playedBy;

    private List<GameAchievementSimpleDto> achievements;

}