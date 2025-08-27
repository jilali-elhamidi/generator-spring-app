package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.dto.TeamDto;
import com.example.modules.project_management.dtosimple.TeamSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends BaseMapper<Team, TeamDto, TeamSimpleDto> {
}