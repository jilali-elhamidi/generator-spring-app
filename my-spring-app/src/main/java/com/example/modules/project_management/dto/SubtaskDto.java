package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.project_management.dtosimple.TaskSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubtaskDto {

    private Long id;

    private String title;

    private String status;

    private TaskSimpleDto parentTask;

    private TeamMemberSimpleDto assignee;

}