package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.SubtaskSimpleDto;

import com.example.modules.project_management.dtosimple.CommentSimpleDto;

import com.example.modules.project_management.dtosimple.TagSimpleDto;

import com.example.modules.project_management.dtosimple.AttachmentSimpleDto;

import com.example.modules.project_management.dtosimple.TimeLogSimpleDto;

import com.example.modules.project_management.dtosimple.MilestoneSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private Date dueDate;

    private String priority;

    private String status;

    private ProjectSimpleDto project;

    private TeamMemberSimpleDto assignee;

    private List<SubtaskSimpleDto> subtasks;

    private List<CommentSimpleDto> comments;

    private List<TagSimpleDto> tags;

    private List<AttachmentSimpleDto> attachments;

    private List<TimeLogSimpleDto> log;

    private MilestoneSimpleDto milestone;

}