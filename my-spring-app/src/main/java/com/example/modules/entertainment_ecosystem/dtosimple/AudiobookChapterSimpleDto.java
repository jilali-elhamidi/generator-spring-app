package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudiobookChapterSimpleDto {

    private Long id;

    private String title;

    private Integer chapterNumber;

    private Integer durationMinutes;

}