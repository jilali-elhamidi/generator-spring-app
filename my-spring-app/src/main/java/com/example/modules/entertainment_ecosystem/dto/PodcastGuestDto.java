package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastEpisodeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PodcastGuestDto {

    private Long id;

    private String name;

    private String bio;

    private PodcastSimpleDto podcast;

    private List<PodcastEpisodeSimpleDto> appearances;

}