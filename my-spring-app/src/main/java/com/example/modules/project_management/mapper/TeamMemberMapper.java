package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.dto.TeamMemberDto;
import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TeamMemberMapper extends BaseMapper<TeamMember, TeamMemberDto, TeamMemberSimpleDto> {
}