package com.example.modules.elearning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.elearning.dtosimple.LessonSimpleDto;

import com.example.modules.elearning.dtosimple.InstructorSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<LessonSimpleDto> lessons;

    private InstructorSimpleDto instructor;

}