package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Task;
import com.example.modules.project_management.dto.TaskDto;
import com.example.modules.project_management.dtosimple.TaskSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseMapper<Task, TaskDto, TaskSimpleDto> {
}