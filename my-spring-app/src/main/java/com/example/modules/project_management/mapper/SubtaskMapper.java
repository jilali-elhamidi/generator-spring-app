package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Subtask;
import com.example.modules.project_management.dto.SubtaskDto;
import com.example.modules.project_management.dtosimple.SubtaskSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SubtaskMapper extends BaseMapper<Subtask, SubtaskDto, SubtaskSimpleDto> {
}