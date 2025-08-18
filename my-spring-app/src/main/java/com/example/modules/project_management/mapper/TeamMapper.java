package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Team;
import com.example.modules.project_management.dto.TeamDto;
import com.example.modules.project_management.dtosimple.TeamSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamDto toDto(Team team);

    TeamSimpleDto toSimpleDto(Team team);

    @InheritInverseConfiguration
    Team toEntity(TeamDto teamDto);

    List<TeamDto> toDtoList(List<Team> teamList);

    List<Team> toEntityList(List<TeamDto> teamDtoList);

    void updateEntityFromDto(TeamDto dto, @MappingTarget Team entity);

}