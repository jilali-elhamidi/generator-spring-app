package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudiobookChapterDto {

    private Long id;

    private String title;

    private Integer chapterNumber;

    private Integer durationMinutes;

    private AudiobookSimpleDto audiobook;

}