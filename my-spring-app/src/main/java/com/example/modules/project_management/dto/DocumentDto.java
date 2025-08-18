package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;

import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;

import com.example.modules.project_management.dtosimple.DocumentTypeSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long id;

    private String title;

    private String fileUrl;

    private Date uploadDate;

    private ProjectSimpleDto project;

    private TeamMemberSimpleDto uploadedBy;

    private DocumentTypeSimpleDto type;

}