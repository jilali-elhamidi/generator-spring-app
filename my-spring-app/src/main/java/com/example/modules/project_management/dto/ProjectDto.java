package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.TaskSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.DocumentSimpleDto;

import com.example.modules.project_management.dtosimple.MilestoneSimpleDto;

import com.example.modules.project_management.dtosimple.ClientSimpleDto;

import com.example.modules.project_management.dtosimple.TeamSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private Long id;

    private String projectName;

    private String description;

    private Date startDate;

    private Date deadline;

    private String status;

    private List<TaskSimpleDto> tasks;

    private List<TeamMemberSimpleDto> teamMembers;

    private TeamMemberSimpleDto projectManager;

    private List<DocumentSimpleDto> documents;

    private List<MilestoneSimpleDto> milestones;

    private ClientSimpleDto client;

    private TeamSimpleDto team;

}