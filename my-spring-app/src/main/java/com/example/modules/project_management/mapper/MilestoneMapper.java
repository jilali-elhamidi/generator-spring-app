package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.dto.MilestoneDto;
import com.example.modules.project_management.dtosimple.MilestoneSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MilestoneMapper extends BaseMapper<Milestone, MilestoneDto, MilestoneSimpleDto> {
}