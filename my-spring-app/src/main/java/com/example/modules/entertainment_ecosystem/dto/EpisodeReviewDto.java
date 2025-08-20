package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeReviewDto {

    private Long id;

    private Integer rating;

    private String comment;

    private Date reviewDate;

    private UserProfileSimpleDto user;

    private EpisodeSimpleDto episode;

}