package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudiobookSimpleDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private Double durationHours;

    private String narrator;

}