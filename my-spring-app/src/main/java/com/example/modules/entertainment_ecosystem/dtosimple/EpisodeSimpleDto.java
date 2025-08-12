package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeSimpleDto {
    private Long id;

    private Integer episodeNumber;

    private String title;

    private String description;

    private Integer durationMinutes;

}