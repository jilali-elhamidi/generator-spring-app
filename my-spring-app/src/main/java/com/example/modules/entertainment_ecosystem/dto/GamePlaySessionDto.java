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
public class GamePlaySessionDto {

    private Long id;

    private Date startTime;

    private Date endTime;

    private Integer score;

    private UserProfileSimpleDto user;

    private VideoGameSimpleDto game;

}