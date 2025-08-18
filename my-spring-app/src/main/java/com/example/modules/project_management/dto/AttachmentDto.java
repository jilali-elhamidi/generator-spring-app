package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.TaskSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {

    private Long id;

    private String fileName;

    private String fileUrl;

    private Date uploadDate;

    private TaskSimpleDto task;

    private TeamMemberSimpleDto uploadedBy;

}