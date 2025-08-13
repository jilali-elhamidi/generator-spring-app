package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserAchievementSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameAchievementDto {

    private Long id;

    private String name;

    private String description;

    private Date achievementDate;

    private VideoGameSimpleDto game;

    private List<UserProfileSimpleDto> earnedBy;

    private List<UserAchievementSimpleDto> userAchievements;

}