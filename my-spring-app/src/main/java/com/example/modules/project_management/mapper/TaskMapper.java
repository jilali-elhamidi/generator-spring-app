package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.dto.TaskDto;
import com.example.modules.project_management.dtosimple.TaskSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto toDto(Task task);

    TaskSimpleDto toSimpleDto(Task task);

    @InheritInverseConfiguration
    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDtoList(List<Task> taskList);

    List<Task> toEntityList(List<TaskDto> taskDtoList);

    void updateEntityFromDto(TaskDto dto, @MappingTarget Task entity);

}