package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Milestone;
import com.example.modules.project_management.dto.MilestoneDto;
import com.example.modules.project_management.dtosimple.MilestoneSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MilestoneMapper {

    MilestoneMapper INSTANCE = Mappers.getMapper(MilestoneMapper.class);

    MilestoneDto toDto(Milestone milestone);

    MilestoneSimpleDto toSimpleDto(Milestone milestone);

    @InheritInverseConfiguration
    Milestone toEntity(MilestoneDto milestoneDto);

    List<MilestoneDto> toDtoList(List<Milestone> milestoneList);

    List<Milestone> toEntityList(List<MilestoneDto> milestoneDtoList);

    void updateEntityFromDto(MilestoneDto dto, @MappingTarget Milestone entity);

}