package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameSessionDto {

    private Long id;

    private Integer score;

    private Integer durationMinutes;

    private Date sessionDate;

    private UserProfileSimpleDto user;

    private VideoGameSimpleDto game;

}