package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.TaskSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDto {

    private Long id;

    private String title;

    private Date dueDate;

    private String status;

    private ProjectSimpleDto project;

    private List<TaskSimpleDto> relatedTasks;

}