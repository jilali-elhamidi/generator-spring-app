package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Subtask;
import com.example.modules.project_management.dto.SubtaskDto;
import com.example.modules.project_management.dtosimple.SubtaskSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SubtaskMapper {

    SubtaskMapper INSTANCE = Mappers.getMapper(SubtaskMapper.class);

    SubtaskDto toDto(Subtask subtask);

    SubtaskSimpleDto toSimpleDto(Subtask subtask);

    @InheritInverseConfiguration
    Subtask toEntity(SubtaskDto subtaskDto);

    List<SubtaskDto> toDtoList(List<Subtask> subtaskList);

    List<Subtask> toEntityList(List<SubtaskDto> subtaskDtoList);

    void updateEntityFromDto(SubtaskDto dto, @MappingTarget Subtask entity);

}