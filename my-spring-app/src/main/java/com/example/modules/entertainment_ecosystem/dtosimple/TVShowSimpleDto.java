package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVShowSimpleDto {

    private Long id;

    private String title;

    private Integer releaseYear;

    private Integer totalSeasons;

    private String synopsis;

}