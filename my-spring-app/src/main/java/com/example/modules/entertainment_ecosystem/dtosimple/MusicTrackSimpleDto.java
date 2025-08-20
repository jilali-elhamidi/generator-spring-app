package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicTrackSimpleDto {

    private Long id;

    private String title;

    private Integer durationSeconds;

    private Date releaseDate;

}