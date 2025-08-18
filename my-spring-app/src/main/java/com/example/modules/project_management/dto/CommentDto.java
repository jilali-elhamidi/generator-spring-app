package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.TaskSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String content;

    private Date commentDate;

    private TeamMemberSimpleDto author;

    private TaskSimpleDto task;

}