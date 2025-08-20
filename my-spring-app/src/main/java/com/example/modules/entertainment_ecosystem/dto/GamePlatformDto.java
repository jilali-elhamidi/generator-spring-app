package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformTypeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamePlatformDto {

    private Long id;

    private String name;

    private List<VideoGameSimpleDto> videoGames;

    private GamePlatformTypeSimpleDto type;

}