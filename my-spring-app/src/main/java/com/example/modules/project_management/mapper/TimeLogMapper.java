package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.TimeLog;
import com.example.modules.project_management.dto.TimeLogDto;
import com.example.modules.project_management.dtosimple.TimeLogSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TimeLogMapper extends BaseMapper<TimeLog, TimeLogDto, TimeLogSimpleDto> {
}