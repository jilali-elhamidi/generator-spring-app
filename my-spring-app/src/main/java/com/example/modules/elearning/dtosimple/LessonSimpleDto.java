package com.example.modules.elearning.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonSimpleDto {
    private Long id;

    private String title;

    private String content;

}