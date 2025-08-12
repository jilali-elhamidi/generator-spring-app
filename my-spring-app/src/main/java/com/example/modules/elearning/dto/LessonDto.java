package com.example.modules.elearning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.elearning.dtosimple.CourseSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private Long id;

    private String title;

    private String content;

    private CourseSimpleDto course;

}