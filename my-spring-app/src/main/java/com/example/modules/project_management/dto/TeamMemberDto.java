package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.project_management.dtosimple.TaskSimpleDto;

import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.CommentSimpleDto;

import com.example.modules.project_management.dtosimple.TeamSimpleDto;

import com.example.modules.project_management.dtosimple.TeamSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDto {

    private Long id;

    private String name;

    private String email;

    private String role;

    private List<TaskSimpleDto> assignedTasks;

    private List<ProjectSimpleDto> projects;

    private List<ProjectSimpleDto> managedProjects;

    private List<CommentSimpleDto> createdComments;

    private TeamSimpleDto team;

    private TeamSimpleDto managedTeam;

}