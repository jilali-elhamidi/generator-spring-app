package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDto {

    private Long id;

    private Integer seasonNumber;

    private TVShowSimpleDto show;

    private List<EpisodeSimpleDto> episodes;

}