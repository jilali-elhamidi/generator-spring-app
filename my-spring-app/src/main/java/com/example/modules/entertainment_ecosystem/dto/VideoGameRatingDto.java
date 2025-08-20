package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoGameRatingDto {

    private Long id;

    private String ratingSystem;

    private String rating;

    private VideoGameSimpleDto game;

}