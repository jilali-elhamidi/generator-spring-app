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
public class InstructorDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CourseSimpleDto> courses;

}