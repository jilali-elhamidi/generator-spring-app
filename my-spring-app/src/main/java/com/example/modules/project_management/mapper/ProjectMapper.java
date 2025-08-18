package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Project;
import com.example.modules.project_management.dto.ProjectDto;
import com.example.modules.project_management.dtosimple.ProjectSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDto toDto(Project project);

    ProjectSimpleDto toSimpleDto(Project project);

    @InheritInverseConfiguration
    Project toEntity(ProjectDto projectDto);

    List<ProjectDto> toDtoList(List<Project> projectList);

    List<Project> toEntityList(List<ProjectDto> projectDtoList);

    void updateEntityFromDto(ProjectDto dto, @MappingTarget Project entity);

}