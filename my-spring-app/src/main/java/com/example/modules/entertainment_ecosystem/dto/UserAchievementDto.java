package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GameAchievementSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAchievementDto {

    private Long id;

    private Date completionDate;

    private UserProfileSimpleDto user;

    private GameAchievementSimpleDto achievement;

}