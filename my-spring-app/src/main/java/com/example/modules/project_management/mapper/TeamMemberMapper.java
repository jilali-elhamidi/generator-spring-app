package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.TeamMember;
import com.example.modules.project_management.dto.TeamMemberDto;
import com.example.modules.project_management.dtosimple.TeamMemberSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TeamMemberMapper {

    TeamMemberMapper INSTANCE = Mappers.getMapper(TeamMemberMapper.class);

    TeamMemberDto toDto(TeamMember teammember);

    TeamMemberSimpleDto toSimpleDto(TeamMember teammember);

    @InheritInverseConfiguration
    TeamMember toEntity(TeamMemberDto teammemberDto);

    List<TeamMemberDto> toDtoList(List<TeamMember> teammemberList);

    List<TeamMember> toEntityList(List<TeamMemberDto> teammemberDtoList);

    void updateEntityFromDto(TeamMemberDto dto, @MappingTarget TeamMember entity);

}