package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.project_management.dtosimple.TaskSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private Long id;

    private String name;

    private List<TaskSimpleDto> tasks;

}