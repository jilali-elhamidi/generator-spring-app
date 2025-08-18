package com.example.modules.project_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.project_management.dtosimple.DocumentSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeDto {

    private Long id;

    private String name;

    private List<DocumentSimpleDto> documents;

}