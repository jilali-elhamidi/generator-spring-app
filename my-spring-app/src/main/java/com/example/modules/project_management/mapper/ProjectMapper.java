package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.dto.ProjectDto;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends BaseMapper<Project, ProjectDto, ProjectSimpleDto> {
}