package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.ProjectSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

    private Long id;

    private String name;

    private String motto;

    private List<TeamMemberSimpleDto> members;

    private TeamMemberSimpleDto teamLead;

    private List<ProjectSimpleDto> teamProjects;

}